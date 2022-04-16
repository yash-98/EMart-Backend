package ctrl;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.StudentBean;
import model.SisModel;

/**
 * Servlet implementation class Sis
 */
@WebServlet({"/Sis", "/Sis/*"})
public class Sis extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sis() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public void init(ServletConfig config) throws ServletException {
	
    	super.init(config);
    	
		ServletContext context = getServletContext();
		
		// Instantiate SisModel thought the singleton method and persist it
		// Instantiate SisModel object
		SisModel sis=SisModel.getInstance();
		context.setAttribute("SIS", sis);
	 }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Hello, Got a " +request.getMethod() +" request from Sis!");
		Writer resOut = response.getWriter();
		
		ServletContext context = this.getServletContext();
		
		//Getting parameters from URL or context------------------------------------------------------
		String namePrefix, credit_taken;
		
		namePrefix = request.getParameter("namePrefix");
		credit_taken = request.getParameter("credit_taken");
				
		//Getting the Model Instance----------------------------------------------------------------------------
		SisModel sis = (SisModel)context.getAttribute("SIS");
		
		Map<String, StudentBean> list = sis.retriveStudent(namePrefix, credit_taken);
		
		//Handle Html So it gives the Ajax Response
		if (request.getParameter("reportAjax") != null && request.getParameter("reportAjax").equals("true")) {
			response.setContentType("application/json");
			
			// Letting Backend know, it's an Ajax Call
			System.out.println("Ajax call");
			
			
			if (list == null || list.isEmpty())
				return;
			
			//Start of JSON
			resOut.append("{ \"students\" : [");
			boolean first = true;
			
			for (Entry<String, StudentBean> e : list.entrySet()) {
				
				resOut.append(first?"":",");
				resOut.append("{\"name\": \"");
				resOut.append(e.getValue().getName());
				resOut.append("\" ,\"credit_taken\": \"");
				resOut.append(Integer.toString(e.getValue().getCredit_taken()));
				resOut.append("\" ,\"credit_graduate\": \"");
				resOut.append(Integer.toString(e.getValue().getCredit_graduate()));
				resOut.append("\" }");
				first = false;
			}
			//End of JSON
			resOut.append("] }");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
