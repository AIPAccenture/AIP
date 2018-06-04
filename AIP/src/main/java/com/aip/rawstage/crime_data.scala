package com.aip.rawstage
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext
import org.apache.hadoop.conf.Configuration
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructType
import java.sql.Timestamp
import java.util.Date
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Row
import org.apache.spark.SparkConf
import java.text.SimpleDateFormat
import org.apache.spark.sql.catalyst.expressions.Cast
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.LongType
import org.apache.hadoop.fs._
import scala.collection.Seq
import java.net.URI
import com.aip.utility.Property;

object crime_data {
  def main(args: Array[String]): Unit = {

    //System.setProperty("hadoop.home.dir", "C:\\Spark");
    //System.setProperty("spark.sql.warehouse.dir", "C:\\spark-warehouse");

    /**
     * Spark configuration setup with serializer
     */
    //val sparkconf = new SparkConf().setAppName("AIP").setMaster("local")

    val sparkconf = new SparkConf().setAppName("AIP")
    sparkconf.set("org.apache.spark.serializer.KryoSerializer", "spark_serializer")
    val sc = new SparkContext(sparkconf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._
    
    //Paths for the source and destination files
    val username = args(0).toLowerCase()
    val src_file = Property.getProperty("src_crime")
    val des_file = Property.getProperty("des_crime")
    val access_key = Property.getProperty("s3a_access_key_"+username)
    val secret_key = Property.getProperty("s3a_secret_key_"+username)


    //Loading the DHM Provider file and writing the file to HDFS location
    sc.hadoopConfiguration.set("fs.s3a.access.key", access_key)
    sc.hadoopConfiguration.set("fs.s3a.secret.key", secret_key)
    val df1 = sqlContext.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").option("delimiter", ",").option("parserLib", "univocity").option("mode", "DROPMALFORMED").load(src_file)
    val df2 = df1.withColumn("GEO_LOCATION", regexp_replace(col("GEO_LOCATION"), "[\\r\\n]", " ")).withColumn("INCIDENT LOCATION", regexp_replace(col("INCIDENT LOCATION"), "[\\r\\n]", " "))
    df2.repartition(1).write.format("com.databricks.spark.csv").option("delimiter", "\t").option("header", "false").save(des_file)

  }

}