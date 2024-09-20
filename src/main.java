import br.com.alura.screenmatch.API.ViaCep;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws Exception {
        try {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o CEP: ");
        String cep = sc.nextLine();
//            String cep = "58015590";
            ViaCep viaCep = new ViaCep(cep);
            System.out.println(viaCep);
            viaCep.salvarEndereco();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }


    }
}
