package com.aip.pdf.extract;
import java.io.File;

import com.aip.pdf.extract.ExtractTextInRectangleAsTable;
public class demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   System.out.println("Simple demo to extract text objects as CSV or xml tables");

	        String file_name = "./county.pdf";
	        System.out.println("File :" + file_name);
	        System.out.println("User coordinates supplied");
	        
	        final File pdf_file = new File(file_name);

	        //if file exists, open and get number of pages
	        if (!pdf_file.exists()) {
	            System.out.println("File " + file_name + " not found");
	        }
	        
	        String outdir1 = "demo";
	        String outdir2 = "population_household";
	        
	        new ExtractTextInRectangleAsTable(file_name,outdir1,"population_",0,696,696,300);
	        new ExtractTextInRectangleAsTable(file_name,outdir1,"population_all_persons_",0,696,696,642);
	        new ExtractTextInRectangleAsTable(file_name,outdir2,"population_household_composition_individual_",0,642,696,588);
	        new ExtractTextInRectangleAsTable(file_name,outdir2,"population_household_composition_unaccompanied_minors_",-11,596,700,580);
	        new ExtractTextInRectangleAsTable(file_name,outdir2,"population_household_composition_family_members_",0,590,696,520);
	        new ExtractTextInRectangleAsTable(file_name,outdir1,"population_veterans_",0,530,696,480);
	        new ExtractTextInRectangleAsTable(file_name,outdir1,"population_gender_",0,480,696,410);
	        new ExtractTextInRectangleAsTable(file_name,outdir1,"population_age_",0,410,696,360);
	        new ExtractTextInRectangleAsTable(file_name,outdir1,"population_chronically_homeless_",0,360,696,290);
	        new ExtractTextInRectangleAsTable(file_name,outdir1,"health_disability_",0,264,696,192);
	        new ExtractTextInRectangleAsTable(file_name,outdir1,"domestic_intimate_violence_",0,180,696,126);
	}

}
