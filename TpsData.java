public class TpsData {
		int[] votes;
		int pemilih;
		int php;
		
		String provinsi;
		String kabupaten;
		String kecamatan;
		String kelurahan;
		int noTps;

		public TpsData(int nPaslon){
			votes = new int[nPaslon];
		}
		
		public void setLokasi(String provinsi, String kabupaten,String kecamatan, String kelurahan, int noTps){
			this.provinsi = provinsi;
			this.kabupaten = kabupaten;
			this.kecamatan = kecamatan;
			this.kelurahan = kelurahan;
			this.noTps = noTps;
		}
		
		public void setVotes(int ...votes){
			for (int i=0; i<votes.length; i++){
				this.votes[i] = votes[i];
			}
		}
		
		public void setPemilih(int p){
			this.pemilih = p;
		}
		
		public void setPenggunaHakPilih(int p){
			this.php = p;
		}
		
		public String toString(){
			String result = provinsi+", "+kabupaten+", "+kecamatan+", "+kelurahan+", "+noTps+", ";
			for (int i=0;i<votes.length; i++){
				result+= votes[i]+", ";
			}
			result += pemilih+", "+php;
			return result;
		}
}
