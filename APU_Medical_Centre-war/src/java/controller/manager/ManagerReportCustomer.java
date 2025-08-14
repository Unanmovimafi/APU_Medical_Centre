package controller.manager;


import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import model.customer.Customer;
import model.customer.CustomerFacade;

@WebServlet(name = "ManagerReportCustomer", urlPatterns = {"/manager/customer/report"})
public class ManagerReportCustomer extends HttpServlet {
    
    @EJB
    private CustomerFacade customerFacade;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

    List<Customer> allCustomer = customerFacade.findAll();

        // inside doGet, after you load allCustomer
        int male = 0, female = 0;
        for (Customer c : allCustomer) {
            String g = (c.getGender() == null) ? "" : c.getGender().trim().toLowerCase();
            if ("male".equals(g)) {
                male++;
            } else if ("female".equals(g)) {
                female++;
            }
        }
// expose to JSP
        request.setAttribute("maleCount", male);
        request.setAttribute("femaleCount", female);

        // ===== 1) Customers per month (last 12 months) =====
    ZoneId zone = ZoneId.systemDefault();
    YearMonth nowYm = YearMonth.now(zone);

    List<YearMonth> last12 = new ArrayList<>();
    for (int i = 11; i >= 0; i--) last12.add(nowYm.minusMonths(i));

    Map<YearMonth, Integer> byMonth = new LinkedHashMap<>();
    for (YearMonth ym : last12) byMonth.put(ym, 0);

    for (Customer c : allCustomer) {
        Date d = c.getCreationDatetime();
        if (d == null) continue;
        LocalDate ld = d.toInstant().atZone(zone).toLocalDate();
        YearMonth ym = YearMonth.from(ld);
        if (byMonth.containsKey(ym)) byMonth.put(ym, byMonth.get(ym) + 1);
    }

    DateTimeFormatter mfmt = DateTimeFormatter.ofPattern("MMM yyyy");
    List<String> monthlyLabels = new ArrayList<>();
    List<Integer> monthlyCounts = new ArrayList<>();
    for (Map.Entry<YearMonth, Integer> e : byMonth.entrySet()) {
        monthlyLabels.add(e.getKey().format(mfmt));
        monthlyCounts.add(e.getValue());
    }

    // ===== 2) Age groups (by dateOfBirth) =====
    // Groups: <18, 18–24, 25–34, 35–44, 45–54, 55–64, 65+
    String[] ageGroupLabels = {"Below 18","18-24","25-34","35-44","45-54","55-64","65+"};
    int[] ageCounts = new int[ageGroupLabels.length];
    LocalDate today = LocalDate.now(zone);  

    for (Customer c : allCustomer) {
        Date dob = c.getDateOfBirth();
        if (dob == null) continue;
        LocalDate birth = dob.toInstant().atZone(zone).toLocalDate();
        if (birth.isAfter(today)) continue;
        int age = Period.between(birth, today).getYears();
        int idx = (age < 18) ? 0 :
                  (age <= 24) ? 1 :
                  (age <= 34) ? 2 :
                  (age <= 44) ? 3 :
                  (age <= 54) ? 4 :
                  (age <= 64) ? 5 : 6;
        ageCounts[idx]++;
    }

    // ===== 3) Blood type distribution =====
    // Using the 8 common types; anything else -> "Unknown"
    String[] bloodLabels = {"O+","O-","A+","A-","B+","B-","AB+","AB-","Unknown"};
    Map<String,Integer> bloodMap = new LinkedHashMap<>();
    for (String b : bloodLabels) bloodMap.put(b, 0);

    Set<String> allowed = new HashSet<>(Arrays.asList("O+","O-","A+","A-","B+","B-","AB+","AB-"));
    for (Customer c : allCustomer) {
        String bt = c.getBloodType();
        String norm = (bt == null) ? "Unknown" : bt.trim().toUpperCase(Locale.ROOT);
        if (!allowed.contains(norm)) norm = "Unknown";
        bloodMap.put(norm, bloodMap.getOrDefault(norm, 0) + 1);
    }
    List<Integer> bloodCounts = new ArrayList<>();
    for (String b : bloodLabels) bloodCounts.add(bloodMap.get(b));

    // ===== 4) Status distribution =====
    // Collect whatever statuses exist (null/empty -> "UNKNOWN")
    Map<String,Integer> statusMap = new LinkedHashMap<>();
    for (Customer c : allCustomer) {
        String s = c.getStatus();
        if (s == null || s.trim().isEmpty()) s = "UNKNOWN";
        s = s.trim().toUpperCase(Locale.ROOT);
        statusMap.put(s, statusMap.getOrDefault(s, 0) + 1);
    }
    List<String> statusLabels = new ArrayList<>(statusMap.keySet());
    List<Integer> statusCounts = new ArrayList<>();
    for (String s : statusLabels) statusCounts.add(statusMap.get(s));

    // ==== Put into request ====
    request.setAttribute("monthlyLabels", monthlyLabels);
    request.setAttribute("monthlyCounts", monthlyCounts);

    request.setAttribute("ageGroupLabels", Arrays.asList(ageGroupLabels));
    request.setAttribute("ageCounts", ageCounts);

    request.setAttribute("bloodLabels", Arrays.asList(bloodLabels));
    request.setAttribute("bloodCounts", bloodCounts);

    request.setAttribute("statusLabels", statusLabels);
    request.setAttribute("statusCounts", statusCounts);

    request.setAttribute("pageContent", "/WEB-INF/views/manager/customer-report.jsp");
    request.getRequestDispatcher("/WEB-INF/layout/layout.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for listing appointments of the logged-in doctor";
    }
}
