package project3;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;


public class HelloWorldEmbedded extends AbstractVerticle  {
	private HttpServer httpServer = null;
    
    public static void main(String[] args) {
    	    Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HelloWorldEmbedded());
    }

    @Override
	public void start() {
    	httpServer = vertx.createHttpServer();
		
		httpServer.requestHandler(new Handler<HttpServerRequest>() {
            @Override
            public void handle(HttpServerRequest request) {
            	HttpServerResponse response = request.response();
            	response.putHeader("content-type", "text/plain");
               	String path = request.path();
               	if (path.equals("/register")) {
               		response.end("this is registration page");
               	}
               	else if (path.equals("/login")) {
               		response.end("this is login page");
               	}
               	else if (path.equals("/post")) {
               		response.end("this is post page");
               	}
               	else {
               		String output = "18655 Project 3 \n"
               				      + "Team 7: Zhongchuan Xu\n"
               				      + "        Wenjie Ma\n"
               				      + "        Ge Jin\n"
               				      + "        Huanwen Chen\n"
               				      + "\n"
               				      + "Team 8: Yiming Zhang\n"
               				      + "        Lai Wei\n"
               				      + "        Xuexiong Zheng\n"
               				      + "        Yangdi Zhou";
                   response.end(output);
               	}
            }
		});
		httpServer.listen(8080);
    }
    
}
