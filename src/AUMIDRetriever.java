import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class AUMIDRetriever {
    public static HashMap<String, String> getAppIDs() throws IOException {
        Process aumidGetProcess = Runtime.getRuntime().exec(new String[]{"powershell.exe", "/c", "Get-StartApps"}); // Executing the command
        aumidGetProcess.getOutputStream().close(); // Getting the results
        
        BufferedReader out = new BufferedReader(new InputStreamReader(aumidGetProcess.getInputStream())); //Processing the results
        String currentLine;
        ArrayList<String> returnedAUMIDs = new ArrayList<String>();
        while ((currentLine = out.readLine()) != null) {
            returnedAUMIDs.add(currentLine);                                //Storing the results
        }
        out.close();
        
        HashMap<String, String> nameToAppID = new HashMap<String, String>();            //Converting the results into key value mappings
        for (int i = 3; i < returnedAUMIDs.size() - 2; i++){
            String[] keyValueSplit = returnedAUMIDs.get(i).split("\\s{2,}");
            if (keyValueSplit.length > 1){
                nameToAppID.put(keyValueSplit[0], keyValueSplit[1]);
            }
        }

        return nameToAppID;                  //returns key value mappings
    }
}