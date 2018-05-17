package com.hers.rawstage
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


object dmh_provider {

  def main(args: Array[String]): Unit = {

    //System.setProperty("hadoop.home.dir", "C:\\SparkDev");
    //System.setProperty("spark.sql.warehouse.dir", "C:\\spark-warehouse");

    /**
     * Spark configuration setup with serializer
     */
    //val sparkconf = new SparkConf().setAppName("HERS").setMaster("local")

    val sparkconf = new SparkConf().setAppName("AIP")
    sparkconf.set("org.apache.spark.serializer.KryoSerializer", "spark_serializer")
    val sc = new SparkContext(sparkconf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    //Loading the Survey file and writing the file to HDFS location
    sc.hadoopConfiguration.set("fs.s3a.access.key", "")
    sc.hadoopConfiguration.set("fs.s3a.secret.key", "")
    val df1 = sqlContext.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").option("delimiter", ",").option("escape", "\\").option("parserLib", "univocity").option("mode", "DROPMALFORMED").load("s3a://prj-11-s3-bkt/Yogesh/Department_of_Mental_Health__DMH__Provider_Directory.csv")
    val df2 = df1.withColumn("Location 1", regexp_replace(col("Location 1"), "[\\r\\n]", " "))
    df2.repartition(1).write.format("com.databricks.spark.csv").option("delimiter", "\t").option("header", "false").save("/user/prj11usr2/dmh_provider")

  }

}