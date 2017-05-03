package image;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
/**
 * 
 * @class:        CutPicture.java
 * @package:      org.com.woaika.mvc.tools
 * @project_name: mobileApp  
 * @author:       bm
 * @date:         2014-11-8 下午03:28:15
 * @Description:  TODO(切图)
 * @Company:   
 * @encode:       
 * @version:      V1.0
 */
public class CutPicture {
	
	//图片路径
	private String srcpath;
	//图片输出位置
	private String subpath;
	//图片后缀类型
	private String imageType;
	// Rectangle(int x, int y, int width, int height)
	/*
	 * height Rectangle 的高度。
	 * width  Rectangle 的宽度。
	 * x      Rectangle 左上角的 X 坐标。
	 * y      Rectangle 左上角的 Y 坐标。
	 */
	private int x;
	private int y;
	private int width;
	private int height;

	public CutPicture() {
	}

	public CutPicture(String srcpath, int x, int y, int width, int height) {
		this.srcpath = srcpath;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getSrcpath() {
		return srcpath;
	}

	public void setSrcpath(String srcpath) {
		this.srcpath = srcpath;
		if (srcpath != null) {
			this.imageType = srcpath.substring(srcpath.indexOf(".") + 1,
					srcpath.length());
		}
	}

	public String getSubpath() {
		return subpath;
	}

	public void setSubpath(String subpath) {
		this.subpath = subpath;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public void cut() throws IOException {
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			is = new FileInputStream(srcpath);
			this.setSrcpath(srcpath);
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(this.imageType);
			ImageReader reader = it.next();
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, this.imageType, new File(subpath));
		} finally {
			if (is != null)
				is.close();
			if (iis != null)
				iis.close();
		}
	}

	public static void main(String[] args) {
		CutPicture o = new CutPicture("E:\\images\\2213.jpg", 800, 100, 800, 100);
		o.setSubpath("E:\\images\\1.jpg");
		try {
			o.cut();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
