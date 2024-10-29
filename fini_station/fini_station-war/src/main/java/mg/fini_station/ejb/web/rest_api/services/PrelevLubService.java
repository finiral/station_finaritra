package mg.fini_station.ejb.web.rest_api.services;

import java.sql.Connection;

import javax.enterprise.inject.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import mg.fini_station.pompes.PrelevLub;
import mg.fini_station.utils.DbConn;
import utilitaire.UtilDB;

@Path("prelev_lub")
public class PrelevLubService {
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response validerPrelevement(PrelevLub p) throws Exception{
        Connection c_perso=null;
        Connection c_mr=null;
        try {
            c_perso=new DbConn().getConnection();
            c_mr=new UtilDB().GetConn();
            p.prelever(c_perso,c_mr);
            ///sortie
            if(p.isStatePair(c_perso)){
                return Response.ok().entity("Vous encaissez "+p.getDifferenceQteVola(c_perso)+" AR").build();
            }
            ///entree
            return Response.ok().entity("Entree prelevement reussi").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage()).build();
            // TODO: handle exception
        }
        finally{
            if(c_perso!=null) c_perso.close();
            if(c_mr!=null) c_mr.close();    
        }

    }
}
