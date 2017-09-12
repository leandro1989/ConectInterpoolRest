import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.spi.scanning.Scanner;

import br.edu.ifpb.entidades.*;
public class ClienteJava {

	private static int HTTP_COD_SUCESSO = 200;

	  public static void main(String[] args) {
		  Jogador teste = new Jogador();
		  String moviment = "Bus";
		  
		  try {
			  //Enviando para o servidor
				Client client = Client.create();
				teste.setJogada(moviment);

				WebResource webResource = client
				   .resource("http://localhost:8080/InterpoolConect2/interpool/enviar");
				
				
				
				//Convertendo ubjeto pra json, ultilizando a bibloteca d google
				Gson gson = new Gson();
				String input = gson.toJson(teste);

				ClientResponse response = webResource.type("application/json")
				   .post(ClientResponse.class, input);

				if (response.getStatus() != 201) {
					throw new RuntimeException("Failed : HTTP error code : "
					     + response.getStatus());
				}

				System.out.println("Output from Server .... \n");
				String output = response.getEntity(String.class);
				System.out.println(output);

			  } catch (Exception e) {

				e.printStackTrace();

			  }

		try {
			//Recebendo do servidor
			Client client = Client.create();

			WebResource webResource = client
			   .resource("http://localhost:8080/InterpoolConect2/interpool/jogada");

			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);

			if (response.getStatus() != HTTP_COD_SUCESSO) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}

			String output = response.getEntity(String.class);

			//System.out.println("Output from Server .... \n"+output);
			Gson gson=new Gson();
			Jogador user=gson.fromJson(output,Jogador.class);	
			
			if (user.getId() == 3){
				System.out.println("foii");				
			}

		  } catch (Exception e) {

			e.printStackTrace();

		  }
		}
	}