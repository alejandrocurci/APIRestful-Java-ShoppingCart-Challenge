package com.desafiospring.challenge.repositories;


import com.desafiospring.challenge.dtos.ClientDataDTO;
import com.desafiospring.challenge.exceptions.ClientException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ClientRepositoryImple implements ClientRepository {

    // receives the ClientDataDTO object and persists it in the CSV file
    @Override
    public void saveClient(ClientDataDTO cliente) throws ClientException {
        BufferedWriter bw = null;
        FileWriter fw = null;
        BufferedReader br = null;
        FileReader fr = null;
        try {
            br = new BufferedReader(new FileReader("src/main/resources/dbClientes.csv"));
            bw = new BufferedWriter(new FileWriter("src/main/resources/dbClientes.csv", true));
            String line = br.readLine();
            while (line != null) {
                line = br.readLine();
            }
            bw.write(System.lineSeparator() + cliente.getId() + "," + cliente.getName() + "," + cliente.getEmail() + "," + cliente.getPassword() + "," + cliente.getCity());
            bw.flush();
        } catch (IOException e) {
            throw new ClientException("Error al guardar el cliente");
        } finally {
            if (fw != null)
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bw != null)
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (fr != null)
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    // loads  the csv file data into a map in memory
    @Override
    public Map<Integer, ClientDataDTO> loadClients() {
        BufferedReader bufferedReader = null;
        Map<Integer, ClientDataDTO> clients = new HashMap<>();
        int fila = 0;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/resources/dbClientes.csv"));
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] cells = line.split(",");
                // skips the first line (titles)
                if (fila > 0) {
                    ClientDataDTO clientDataDTO = new ClientDataDTO();
                    clientDataDTO.setId(Integer.parseInt(cells[0]));
                    clientDataDTO.setName(cells[1]);
                    clientDataDTO.setEmail(cells[2]);
                    clientDataDTO.setPassword(cells[3]);
                    clientDataDTO.setCity(cells[4]);
                    clients.put(clientDataDTO.getId(), clientDataDTO);
                }
                line = bufferedReader.readLine();
                fila++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return clients;
    }


}
