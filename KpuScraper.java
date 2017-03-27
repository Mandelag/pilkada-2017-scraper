import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileReader;

import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;


/**
 * Work with this bash command:
 * 
 * cd ~/workspace/pilkada
 * for i in $(cat list_kelurahan.txt); 
 * do
 *   echo "REGION=$i" >> pilkada_gabung_ringkas2.txt;
 *   cat "pilkada2017.kpu.go.id/hasil/t1/$i" |grep "rekapHasil.push([0-9]*)"|tr -d '\040\011' >> pilkada_gabung_ringkas2.txt;
 *   echo "PEMILIH" >> pilkada_gabung_ringkas2.txt;
 *   cat "pilkada2017.kpu.go.id/hasil/t1/$i" | grep "                                                   <td>[0-9][0-9]*</td>" | tr -d '\040\011' >> pilkada_gabung_ringkas2.txt;
 *   echo "REGION_DONE" >> pilkada_gabung_ringkas2.txt
 * done
 * 
 * Return a txt (csv actually) files with province, regencies, districts, village, tps number, votes (from 1 to 10), voter, vote user.
 *
 * @author: Keenan Mandela Gebze
 * @version: 1.0.0
 *
 */
public class KpuScraper {
        
    public static void main( String[] args ) throws IOException {

		if(args.length < 1){
			System.err.println("Usage:\n    KpuScraper2 [-k] file");
			return;
		}
		String file = "";
		boolean rekapKelurahan = false;
		
		if(args.length == 1){
			file = args[0];
		}else if(args.length > 1){
			if(args[0].equals("-k")){
				rekapKelurahan = true;
			}
			file = args[args.length-1]; 
		}
		
        BufferedReader br = new BufferedReader(new FileReader(file));
        
        String result = "";
        String line = "";
        String line2 = "";
		ArrayList<TpsData> hehe = new ArrayList<>();	


		int tpsCounter = 0;
		
		int[] votes = new int[10];
		int[] voters = new int[2];
		TpsData td = null;
		
        while( (line = br.readLine()) != null) {
			if( line.contains("REGION=") ){
				
				String url = line.split("=")[1];
				String loc[] = url.replace("_", " ").split("/");
				int len = loc.length;
				String provinsi = loc[len-4];
				String kabupaten = loc[len-3];
				String kecamatan = loc[len-2];
				String kelurahan = loc[len-1];
				tpsCounter = 0;
				hehe.clear();
								
				while ( (line2 = br.readLine()) != null){
					if( ! "PEMILIH".equals(line2) ){
						votes[0] = Integer.parseInt((line2.split("\\(")[1].split("\\)")[0]));
						votes[1] = Integer.parseInt(br.readLine().split("\\(")[1].split("\\)")[0]);
						votes[2] = Integer.parseInt(br.readLine().split("\\(")[1].split("\\)")[0]);
						votes[3] = Integer.parseInt(br.readLine().split("\\(")[1].split("\\)")[0]);
						votes[4] = Integer.parseInt(br.readLine().split("\\(")[1].split("\\)")[0]);
						votes[5] = Integer.parseInt(br.readLine().split("\\(")[1].split("\\)")[0]);
						votes[6] = Integer.parseInt(br.readLine().split("\\(")[1].split("\\)")[0]);
						votes[7] = Integer.parseInt(br.readLine().split("\\(")[1].split("\\)")[0]);
						votes[8] = Integer.parseInt(br.readLine().split("\\(")[1].split("\\)")[0]);
						votes[9] = Integer.parseInt(br.readLine().split("\\(")[1].split("\\)")[0]);
						tpsCounter++;
						
						td = new TpsData(10);
						td.setLokasi(provinsi, kabupaten, kecamatan, kelurahan, tpsCounter);
						td.setVotes(votes[0], votes[1], votes[2], votes[3], votes[4], votes[5], votes[6], votes[7], votes[8], votes[9]);
						hehe.add(td);
						
					}else{
						break;
					}
				}
				
				for(int i=0; i<tpsCounter-1; i++){
					
					TpsData td1 = hehe.get(i);
					
					String a = "";
					String b = "";
					try {
						a = br.readLine();
						a = a.split(">")[1];					
						b = br.readLine();
						b = b.split(">")[1];
						
						a = a.split("<")[0];
						b = b.split("<")[0];
					}catch(ArrayIndexOutOfBoundsException e){
						System.err.println(a);
						System.err.println(b);
						System.err.println(e);
						System.err.println(td1);
						a="0";
						b="0";
						System.exit(1);
					}
					//System.err.println(a);
					//System.err.println(b);
					
					//mungkin regexnya tak cuma \\. melainkan semua non-digit..
					if(a.contains(".")){
						a = a.replaceAll("\\.","");
					}
					voters[0] = Integer.parseInt(a);
						
					if(b.contains(".")){
						b = b.replaceAll("\\.","");
					}
					voters[1] = Integer.parseInt(b);
					
					//System.out.printf("%d, %d", voters[0], voters[1]);
					
					
					td1.setPemilih(voters[0]);
					td1.setPenggunaHakPilih(voters[1]);
					System.out.println(td1.toString().replace(", ", ",").trim());
				}
						
				br.readLine(); //region_done
			}
		}

	}
}
