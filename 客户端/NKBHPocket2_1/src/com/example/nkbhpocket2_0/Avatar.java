package com.example.nkbhpocket2_0;

public class Avatar{


    // Fields    

     private Integer imgId;
     private String imgName;


    // Constructors

    /** default constructor */
    public Avatar() {
    }

    
    /** full constructor */
    public Avatar(String imgName) {
        this.imgName = imgName;
    }

   
    // Property accessors

    public Integer getImgId() {
        return this.imgId;
    }
    
    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public String getImgName() {
        return this.imgName;
    }
    
    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
   

}