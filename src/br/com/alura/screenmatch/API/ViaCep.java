package br.com.alura.screenmatch.API;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import netscape.javascript.JSObject;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class ViaCep {
    private String cep;
    private String API_URL = "https://viacep.com.br/ws/";
    private JsonObject endereco;

    public ViaCep(String cep) throws Exception {
        if(cep.length() != 8){
            throw new IllegalArgumentException("CEP inválido");
        }
        this.cep = cep;
        this.getEndereco();
    }

    private void getEndereco() throws Exception {
        this.API_URL += this.cep + "/json";
        URI uri = new URI(this.API_URL);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != HttpURLConnection.HTTP_OK){
            throw new Exception("Erro na requisição");
        }


        this.endereco = JsonParser.parseString(response.body()).getAsJsonObject();
    }

    private String getUF(){
        return this.endereco.get("uf").getAsString();
    }

    private String getRegiao(){
        return this.endereco.get("regiao").getAsString();
    }

    private String getLocalidade(){
        return this.endereco.get("localidade").getAsString();
    }

    public void salvarEndereco(){
        UUID uuid = UUID.randomUUID();
        String fileName = "Endereco"+ this.getRegiao() + this.getUF() + this.getLocalidade() + uuid + ".json";
        try(FileWriter file = new FileWriter(fileName)){
            file.write(this.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "{\"Cep\":"
                + this.endereco
                + "}";
    }
}
