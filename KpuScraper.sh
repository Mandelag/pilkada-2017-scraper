#Change with your own workspace
WORKSPACE=~/workspace/pilkada-new
OUTPUT_DIR=output
VILLAGE_LIST=output/list_kelurahan.txt
SUMMARY=output/pilkada_gabung_ringkas2.txt
RESULT_TPS=output/hasil_TPS.csv
RESULT_KEL=output/hasil_KELURAHAN.csv

cd $WORKSPACE
rm -rf $OUTPUT_DIR
mkdir $OUTPUT_DIR

#Download the data
#cd $WORKSPACE
#wget -r --no-parent https://pilkada2017.kpu.go.id/hasil/t1/dki_jakarta

#List all of the villages
cd $WORKSPACE/pilkada2017.kpu.go.id/hasil/t1
find */*/*/* > $WORKSPACE/$VILLAGE_LIST

#If you want to select only jakarta.
#find dki_jakarta/*/*/* > $WORKSPACE/$VILLAGE_LIST

#Initial scrape (before using the java program)
cd $WORKSPACE
for i in $(cat $VILLAGE_LIST); 
do
  echo "REGION=$i" >> $SUMMARY;
  cat "pilkada2017.kpu.go.id/hasil/t1/$i" |grep "rekapHasil.push([0-9]*)"|tr -d '\040\011' >> $SUMMARY;
  echo "PEMILIH" >> $SUMMARY;
  #cat "pilkada2017.kpu.go.id/hasil/t1/$i" | grep "                                                   <td>[0-9][0-9]*</td>" | tr -d '\040\011' >> $SUMMARY;
  #cat "pilkada2017.kpu.go.id/hasil/t1/$i" | grep "                                                    <td class=\"text-right\">[0-9][0-9]*</td>" | tr -d '\040\011' >> $SUMMARY;
  cat "pilkada2017.kpu.go.id/hasil/t1/$i" | grep "^ \{52\}<td class=\"text-right\">[0-9]*.[0-9]*</td>" | tr -d '\040\011' >> $SUMMARY;
  echo "REGION_DONE" >> $SUMMARY
done

#finally, using the KpuScraper.java
javac KpuScraper2.java
java KpuScraper2 $SUMMARY > $RESULT_TPS

python Agregator.py $RESULT_TPS > $RESULT_KEL
