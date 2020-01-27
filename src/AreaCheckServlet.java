import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AreaCheckServlet extends HttpServlet {
    ArrayList<Double> xArr;
    double y;
    int r;

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        xArr = new ArrayList<>();
        String[] xes = req.getParameterValues("x");

        for (int i = 0; i < xes.length; i++) {
            xArr.add(Double.parseDouble(xes[i]));
        }

        y = Double.parseDouble(req.getParameter("y"));
        r = Integer.parseInt(req.getParameter("r"));

        ArrayList<Pair<String, Result>> results = (ArrayList<Pair<String, Result>>) getServletContext().getAttribute(LabConst.RESULT_ARRAY);

        String requestedSessionId = req.getRequestedSessionId();

        if(results == null){
            results = new ArrayList<>();
        }
        for (int i = 0; i < xArr.size(); i++) {
            results.add(new Pair<>(req.getRequestedSessionId(),new Result(xArr.get(i), y,r ,checkPoint(xArr.get(i), y, r) )));
        }
        
        
        
        updateResults(results);

        System.out.println(req.getParameter("x0")+req.getParameter("x1"));
        System.out.println(getServletContext().getContextPath());
        resp.sendRedirect(getServletContext().getContextPath() + "/home.jsp");

    }


    private boolean checkPoint(double x, double y, int r){
        if(y <= 0.5*x + 0.5*r && x <= 0 && y >= 0){
            return true;
        }
        if((y >= (double) (-r)) && (y <= 0) && (x >= ((double)-r*0.5)) && (x <= 0)){
            return true;
        }
        if(y*y +  x*x <= Math.pow((double)r/2, 2) && y <= 0 && x >= 0){
            return true;
        }

        return false;
    }

    private void updateResults(ArrayList<Pair<String, Result>> results){
        //Collections.reverse(results);
        getServletContext().setAttribute(LabConst.RESULT_ARRAY, results);
    }
}
