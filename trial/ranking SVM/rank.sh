#!/bin/sh
#this script is to run ranking SVM. Read from ~/trial/tesetSet and produce the results in ~/trial/result
echo "runing the rank ..."
for ((i=0; i<258; i++))
do
    /home/zzf/RankingSVM/svm_rank_classify /home/zzf/RankingSVM/trial3/testSet/${i}.dat /home/zzf/RankingSVM/trial3/model.dat /home/zzf/RankingSVM/trial3/result/result${i}.txt
done 
