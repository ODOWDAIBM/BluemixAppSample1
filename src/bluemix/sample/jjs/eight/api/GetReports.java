package bluemix.sample.jjs.eight.api;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bluemix.sample.jjs.eight.api.JSON.ColourByReg;
import bluemix.sample.jjs.eight.api.JSON.Report;
import bluemix.sample.jjs.eight.api.JSON.TaxStatus;
import bluemix.sample.jjs.eight.datastorage.StorageLayer;
import bluemix.sample.jjs.eight.datastorage.StorageSingleton;

import com.mongodb.DB;


@Path("/getReport")
public class GetReports {

	StorageLayer DB = StorageSingleton.sl;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Report query(final Report query) {
		
		Report response = new Report();

		if (query.VRM != null && query.VRMs == null) {
			response.VRM = query.VRM;
			response = DB.getReport(query.VRM, response);
		} else if (query.VRM == null
				&& query.VRMs != null) {

			List<Report> resultsList = new LinkedList<Report>();

			for (String s : query.VRMs) {
				Report nestedResponse = new Report();
				nestedResponse.VRM = s;
				nestedResponse = DB.getReport(s, nestedResponse);
				resultsList.add(nestedResponse);
			}
			response.reports = resultsList;
		}

		return response;
		
	}

}
