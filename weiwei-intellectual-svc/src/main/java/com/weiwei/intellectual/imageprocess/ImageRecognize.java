package com.weiwei.intellectual.imageprocess;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageRecognize {
	public static String getCodeFromImage(BufferedImage img){
		int height = img.getHeight();
		int width = img.getWidth();
		
		int[][] image_bnw = new int[height][width];
		
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				int color = img.getRGB(j, i);
				int bule = color & 0xff;
				int green = (color & 0xff00) >> 8;
				int red = (color & 0xff0000) >> 16;
				
				if(red < 140 || green < 140 || bule < 140){
					image_bnw[i][j] = 0;
				}
				else{
					image_bnw[i][j] = 255;
				}
			}
		}
		
		
		// crop essence processing
		int height_crop = height;
		int width_crop = width;
		int k;
		// crop left columns
		boolean flag = true; //all white
		while(flag){
			for(k=0; k<height_crop; k++){
				if(image_bnw[k][0] == 0){ 
					flag = false;
					break;
				}
			}
			if(flag){
				int[][] subImage = new int[height_crop][--width_crop];
				for(int i=0; i<height_crop; i++){
					subImage[i] = Arrays.copyOfRange(image_bnw[i], 1, width_crop+1);
				}
				image_bnw = subImage;
			}
		}
		// crop right columns
		flag = true;
		while(flag){
			for(k=0; k<height_crop; k++){
				if(image_bnw[k][width_crop-1] == 0){
					flag = false;
					break;
				}
			}
			if(flag){
				int[][] subImage = new int[height_crop][--width_crop];
				for(int i=0; i<height_crop; i++){
					subImage[i] = Arrays.copyOfRange(image_bnw[i], 0, width_crop);
				}
				image_bnw = subImage;
			}
		}
		// crop head rows
		flag = true;
		while(flag){
			for(k=0; k<width_crop; k++){
				if(image_bnw[0][k] == 0){
					flag = false;
					break;
				}
			}
			if(flag){
				int[][] subImage = new int[--height_crop][width_crop];
				for(int i=0; i<height_crop; i++){
					subImage[i] = Arrays.copyOfRange(image_bnw[i+1], 0, width_crop);
				}
				image_bnw = subImage;
			}
		}
		// crop bottom rows
		flag = true;
		while(flag){
			for(k=0; k<width_crop; k++){
				if(image_bnw[height_crop-1][k] == 0){
					flag = false;
					break;
				}
			}
			if(flag){
				int[][] subImage = new int[--height_crop][width_crop];
				for(int i=0; i<height_crop; i++){
					subImage[i] = Arrays.copyOfRange(image_bnw[i], 0, width_crop);
				}
				image_bnw = subImage;
			}
		}
		
		// pattern recognition
		List<int[][]> patternList = new ArrayList<int[][]>();
		patternList.add(patternsNumber.pattern8);
		patternList.add(patternsNumber.pattern6);
		patternList.add(patternsNumber.pattern0);
		patternList.add(patternsLetter.patternB);
		patternList.add(patternsLetter.patternE);
		patternList.add(patternsLetter.patternD);
		patternList.add(patternsLetter.patternL);
		patternList.add(patternsNumber.pattern1);
		patternList.add(patternsNumber.pattern2);
		patternList.add(patternsNumber.pattern3);
		patternList.add(patternsNumber.pattern4);
		patternList.add(patternsNumber.pattern5);
		patternList.add(patternsLetter.patternZ);
		patternList.add(patternsNumber.pattern7);
		patternList.add(patternsNumber.pattern9);
		patternList.add(patternsLetter.patternQ);
		patternList.add(patternsLetter.patternO);
		patternList.add(patternsLetter.patternG);
		patternList.add(patternsLetter.patternC);
		patternList.add(patternsLetter.patternT);
		patternList.add(patternsLetter.patternJ);
		patternList.add(patternsLetter.patternW);
		patternList.add(patternsLetter.patternV);
		patternList.add(patternsLetter.patternA);
		patternList.add(patternsLetter.patternK);
		patternList.add(patternsLetter.patternM);
		patternList.add(patternsLetter.patternN);
		patternList.add(patternsLetter.patternH);
		patternList.add(patternsLetter.patternR);
		patternList.add(patternsLetter.patternP);
		patternList.add(patternsLetter.patternF);
		patternList.add(patternsLetter.patternS);
		patternList.add(patternsLetter.patternU);
		patternList.add(patternsLetter.patternX);
		patternList.add(patternsLetter.patternY);
		patternList.add(patternsLetter.patternI);
		String[] patternValueList = new String[]{"8","6","0","B","E","D","L","1","2","3","4","5","Z","7","9","Q","O","G","C","T","J","W","V","A","K","M","N","H","R","P","F","S","U","X","Y","I"};
		k=0;
		int patternID_index = 0;
		int lastPatternRightSide = 0;
		String value_string = "";
		while(k < width_crop){
			for(patternID_index=0; patternID_index<patternList.size(); patternID_index++){
				boolean isMatch = false;
				
				int[][] pattern  = patternList.get(patternID_index);
				int patternHeight = pattern.length;
				int patternWidth = pattern[0].length;
				
				if(patternHeight > height_crop || patternWidth > width_crop){
					continue;
				}
				if(k > (width_crop-patternWidth)){
					continue;
				}
				
				for(int i=0; i<=(height_crop - patternHeight); i++){
					int patternBlackPoint = 0;
					int matchPoint = 0;
					
					for(int pattern_index_x = 0; pattern_index_x < patternWidth; pattern_index_x++){
						for(int pattern_index_y = 0; pattern_index_y < patternHeight; pattern_index_y++){
							if(pattern[pattern_index_y][pattern_index_x] == 0){
								patternBlackPoint++;
								if(image_bnw[i+pattern_index_y][k+pattern_index_x] == 0){
									matchPoint++;
								}
							}
						}
					}
					
					if(matchPoint*1.00/patternBlackPoint >= 0.98){
						if(patternValueList[patternID_index].equalsIgnoreCase("I")){
							if(k+patternWidth <= lastPatternRightSide){
								continue;
							}
						}
						
						value_string += patternValueList[patternID_index];
						lastPatternRightSide = k + patternWidth;
						k += Math.round(patternWidth/2.5);
						isMatch = true;
						break;
					}
				}
				
				if(isMatch){
					break;
				}
			}
			if(value_string.length() == 4)
				break;
			k++;
		}
		
		return value_string;
	}
}
