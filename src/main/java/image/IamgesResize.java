package image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * @class: IamgesResize.java
 * @package: org.com.woaika.mvc.tools
 * @project_name: mobileApp
 * @author: bm
 * @date: 2014-9-8 下午03:32:04
 * @Description: TODO()
 * @Company:
 * @encode:
 * @version: V1.0
 */
public class IamgesResize {

	BufferedImage bufImage; // 原始图片
	int width; // 缩放的宽度
	int height; // 缩放的高度

	public IamgesResize() {
	}

	public IamgesResize(String srcPath, int width, int height) {
		this.width = width;
		this.height = height;
		try {
			this.bufImage = ImageIO.read(new File(srcPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 实现图像的等比缩放和缩放后的截取，如果高度的值和宽度一样，则缩放按设置的值缩放
	 * (只控制宽度的大小，高度的值设置不生效(只有高度的值和宽度的一样才生效)， 高度自动按比例缩放；如果缩放的图片小于你设置的值则保存原图大小)
	 * 
	 * @param inFilePath
	 *            要缩放图片文件的路径
	 * @param outFilePath
	 *            缩放后保存图片输出的路径
	 * @param width
	 *            要截取宽度
	 * @param hight
	 *            要截取的高度
	 * @throws Exception
	 */

	public static void zoomOutImage(String inFilePath, String outFilePath,
			int width, int hight, boolean smooth) throws Exception {
		int maxHight = 500; // 设置最大的图片高度;

		File file = new File(inFilePath);
		InputStream in = new FileInputStream(file);
		File saveFile = new File(outFilePath);
		BufferedImage srcImage = ImageIO.read(in);

		String gif = inFilePath.substring(inFilePath.lastIndexOf(".") + 1,
				inFilePath.length());

		if ((gif.equals("gif") || gif.equals("GIF")) && smooth == true) // gif动态图片的处理
		{

		} else {
			// 如果宽度和高度一样 或者图片的规格为 images_120 时不按等比缩放，如果需要等比缩放, 则将下面的 if 语句注释即可
			if (width != hight && !outFilePath.contains("images_120")) {
				double sx = (double) width / srcImage.getWidth();
				hight = (int) (srcImage.getHeight() * sx);
			}

			if (width > 0 || hight > 0) {
				// 原图的大小
				int sw = srcImage.getWidth();
				int sh = srcImage.getHeight();
				// 如果原图像的大小小于要缩放的图像大小，直接将要缩放的图像复制过去
				if (sw > width && sh > hight) {
					srcImage = rize(srcImage, width, hight);
				} else {
					String fileName = saveFile.getName();
					String formatName = fileName.substring(fileName
							.lastIndexOf('.') + 1);
					ImageIO.write(srcImage, formatName, saveFile);
					return;
				}
			}
			// 缩放后的图像的宽和高
			int w = srcImage.getWidth();
			int h = srcImage.getHeight();

			// 如果缩放后的图像和要求的图像宽度一样，就对缩放的图像的高度进行截取
			if (w == width) {
				// 计算 X轴坐标
				int x = 0;

				// 如果图片超过指定高度则截取一定的高度
				if (h >= maxHight && width != 600) // 图片为600 的不需要截取高度
				{
					int y = h / 2 - hight / 2;
					saveSubImage(srcImage,
							new Rectangle(x, y, width, maxHight), saveFile);
				} else {
					int y = h / 2 - hight / 2;
					saveSubImage(srcImage, new Rectangle(x, y, width, hight),
							saveFile);
				}

			}
			// 否则如果是缩放后的图像的高度和要求的图像高度一样，就对缩放后的图像的宽度进行截取
			else if (h == hight) {
				// 计算X轴坐标
				int x = w / 2 - width / 2;
				int y = 0;
				saveSubImage(srcImage, new Rectangle(x, y, width, hight),
						saveFile);
			}
			in.close();
		}
	}

	/**
	 * @param srcPath
	 *            图片的绝对路径
	 * @param width
	 *            图片要缩放的宽度
	 * @param height
	 *            图片要缩放的高度
	 * @param rizeType
	 *            图片要缩放的类型（1：宽度固定，高度自动 2：按宽度和高度比例缩小）
	 * @return
	 */
	public static BufferedImage rize(BufferedImage srcBufImage, int width,
			int height) {
		BufferedImage bufTarget = null;
		int type = srcBufImage.getType();
		double sx = (double) width / srcBufImage.getWidth();
		double sy = (double) height / srcBufImage.getHeight();

		if (type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = srcBufImage.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(width,
					height);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			bufTarget = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			bufTarget = new BufferedImage(width, height, type);
		}

		Graphics2D g = bufTarget.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(srcBufImage,
				AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return bufTarget;
	}

	/**
	 * 实现缩放后的截图
	 * 
	 * @param image
	 *            缩放后的图像
	 * @param subImageBounds
	 *            要截取的子图的范围
	 * @param subImageFile
	 *            要保存的文件
	 * @throws IOException
	 */
	private static void saveSubImage(BufferedImage image,
			Rectangle subImageBounds, File subImageFile) throws IOException {
		if (subImageBounds.x < 0 || subImageBounds.y < 0
				|| subImageBounds.width - subImageBounds.x > image.getWidth()
				|| subImageBounds.height - subImageBounds.y > image.getHeight()) {
			return;
		}
		BufferedImage subImage = image.getSubimage(subImageBounds.x,
				subImageBounds.y, subImageBounds.width, subImageBounds.height);
		String fileName = subImageFile.getName();
		String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
		ImageIO.write(subImage, formatName, subImageFile);
	}

	/**
	 * 针对书签截屏的等比缩放(等比缩放，不失真)
	 * 
	 * @param src
	 *            源图片文件完整路径
	 * @param dist
	 *            目标图片文件完整路径
	 * @param width
	 *            缩放的宽度
	 * @param heightw
	 *            缩放的高度
	 */
	public static void createThumbnail(String src, String dist, float width,
			float height) {
		try {
			File srcfile = new File(src);
			if (!srcfile.exists()) {
				return;
			}
			BufferedImage image = ImageIO.read(srcfile);

			// 获得缩放的比例
			double ratio = 1.0;
			// 判断如果高、宽都不大于设定值，则不处理
			if (image.getHeight() > height || image.getWidth() > width) {
				if (image.getHeight() > image.getWidth()) {
					ratio = height / image.getHeight();
				} else {
					ratio = width / image.getWidth();
				}
			}
			// 计算新的图面宽度和高度
			int newWidth = (int) (image.getWidth() * ratio);
			int newHeight = (int) (image.getHeight() * ratio);

			BufferedImage bfImage = new BufferedImage(newWidth, newHeight,
					BufferedImage.TYPE_INT_RGB);
			bfImage.getGraphics().drawImage(
					image.getScaledInstance(newWidth, newHeight,
							Image.SCALE_SMOOTH), 0, 0, null);

			FileOutputStream os = new FileOutputStream(dist);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			encoder.encode(bfImage);
			os.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 实现图像的等比缩放和缩放后的截取，如果高度的值和宽度一样，则缩放按设置的值缩放
	 * (只控制宽度的大小，高度的值设置不生效(只有高度的值和宽度的一样才生效)， 高度自动按比例缩放；如果缩放的图片小于你设置的值则保存原图大小)
	 * 如果要缩放的宽度和高度相等则不按比例缩放；直接缩小图片
	 * 
	 * @param filepath
	 *            要缩放图片文件的路径
	 * @param saveFilePath
	 *            缩放后保存图片输出的路径
	 * @param imgWidth
	 *            要缩放的宽度
	 * @param imHeight
	 *            要缩放的高度
	 * @return
	 * @throws Exception
	 */
	public static boolean resizeFile(String filepath, String saveFilePath,
			int imgWidth, int imHeight) throws Exception {
		String suffix=null;
		File readfile = new File(filepath);
		if (!readfile.isDirectory()) {
			 suffix = readfile.getName().substring(
					readfile.getName().lastIndexOf(".") + 1,
					readfile.getName().length()); // 获取文件的后缀
			// 是图片类型的才执行缩放
			if (isFromImgUrl(suffix)) {
				IamgesResize.zoomOutImage(filepath ,
						saveFilePath , imgWidth, imHeight,
						true);
				IamgesResize.createThumbnail(filepath ,
						saveFilePath , imgWidth, imHeight);
			}
			return true;
		}else{
			return false;
		}
	}

	public static boolean bathResizeFile(String filepath, String saveFilePath,
			int imgWidth, int imHeight) throws Exception {
		String suffix=null;
		File file = new File(filepath);
		if (file.isDirectory()) { // 如果path表示的是否是文件夹，是返回true
			String[] filelist = file.list();
			int count = 0;
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + filelist[i]);
				if (!readfile.isDirectory()) {
					 suffix = readfile.getName().substring(
							readfile.getName().lastIndexOf(".") + 1,
							readfile.getName().length()); // 获取文件的后缀
					// 是图片类型的才执行缩放
					if (isFromImgUrl(suffix)) {
						count++;
						IamgesResize.zoomOutImage(
								filepath , saveFilePath
										, imgWidth,
								imHeight, true);
						IamgesResize.createThumbnail(
								filepath, saveFilePath
										, imgWidth,
								imHeight);
					}

				}
			}
		}
		return true;
	}

	/**
	 * 判断网址是不是图片类型。
	 * 
	 * @param fromUrl
	 * @return
	 */
	public static boolean isFromImgUrl(String imgfuffix) {
		boolean isImage = false;

		// 支持的图片后缀。
		String[] imgSuffixs = { "jpg", "JPG", "jpeg", "JPEG", "gif", "GIF",
				"png", "PNG", "bmp", "BMP" };
		for (int i = 0; i < imgSuffixs.length; i++) {
			if (imgfuffix.equals(imgSuffixs[i])) {
				isImage = true;
				break;
			}
		}
		return isImage;
	}

}
