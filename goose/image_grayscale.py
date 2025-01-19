import cv2
import numpy as np

# 读取彩色图像
image = cv2.imread('./image/Goose1.png')

# !使用加权平均法将图像转换为灰度图像(自动)
gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# !添加高斯模糊以去除高斯噪声
gaussian_blur_image = cv2.GaussianBlur(gray_image, (5, 5), 0)

# # 或者手动实现加权平均法
# gray_image_manual = 0.299 * image[:, :, 2] + 0.587 * image[:, :, 1] + 0.114 * image[:, :, 0]
# gray_image_manual = gray_image_manual.astype('uint8')

# !定义自适应阈值分割函数
def adaptive_threshold(image, block_size=165, C=0.1):
    # 获取图像尺寸
    height, width = image.shape
    # 创建结果图像
    result = np.zeros_like(image)
    
    # 遍历图像中的每个像素点
    for i in range(height):
        for j in range(width):
            # 计算当前像素点的邻域范围
            x_start = max(0, i - block_size // 2)
            x_end = min(height, i + block_size // 2 + 1)
            y_start = max(0, j - block_size // 2)
            y_end = min(width, j + block_size // 2 + 1)
            
            # 提取邻域
            neighborhood = image[x_start:x_end, y_start:y_end]
            
            # 计算邻域的高斯加权平均值
            weights = np.exp(-((np.arange(neighborhood.shape[0])[:, np.newaxis] - neighborhood.shape[0] // 2) ** 2 +
                              (np.arange(neighborhood.shape[1]) - neighborhood.shape[1] // 2) ** 2) / (2 * (block_size // 2) ** 2))
            weights /= weights.sum()
            T = np.sum(neighborhood * weights)
            
            # 阈值分割
            if image[i, j] > T - C:
                result[i, j] = 255
            else:
                result[i, j] = 0
    
    return result

# !应用自适应阈值分割
adaptive_threshold_image = adaptive_threshold(gaussian_blur_image)

# !添加开操作以去除小的噪声点
kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (7, 6))
opening_image = cv2.morphologyEx(adaptive_threshold_image, cv2.MORPH_OPEN, kernel)

# !获取最大连通域
num_labels, labels, stats, centroids = cv2.connectedComponentsWithStats(opening_image, connectivity=8)
largest_label = 1 + np.argmax(stats[1:, cv2.CC_STAT_AREA])
max_connected_domain_image = np.zeros_like(opening_image)
max_connected_domain_image[labels == largest_label] = 255

# !添加闭操作以填充小的空洞
closing_image = cv2.morphologyEx(max_connected_domain_image, cv2.MORPH_CLOSE, kernel)

# !孔洞填充
def fill_holes(image, kernel, max_iterations=100000):
    # 获取图像尺寸
    height, width = image.shape
    # 创建结果图像
    result = image.copy()
    
    for _ in range(max_iterations):
        # 膨胀操作
        dilated = cv2.dilate(result, kernel, iterations=1)
        # 与原始图像的补集进行与操作
        mask = cv2.bitwise_and(dilated, cv2.bitwise_not(image))
        # 更新结果图像
        result = cv2.bitwise_or(result, mask)
        
        # 检查是否不再改变
        if np.array_equal(result, dilated):
            break
    
    return result

filled_image = fill_holes(closing_image, kernel)

# !提取轮廓
contours, hierarchy = cv2.findContours(filled_image, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

# !绘制轮廓
contour_image = np.zeros_like(image)
cv2.drawContours(contour_image, contours, -1, (0, 255, 0), 1)

# 保存轮廓图片
cv2.imwrite('./image/contour_image.png', contour_image)

# !显示结果
# *显示原始图像
cv2.imshow('Original Image', image)
# *显示灰度图像(自动)
cv2.imshow('Gray Image (OpenCV)', gray_image)
# *显示高斯模糊后的图像
cv2.imshow('Gaussian Blur Image', gaussian_blur_image)
# *显示自适应阈值分割后的图像
cv2.imshow('Adaptive Threshold Image', adaptive_threshold_image)
# *显示开操作后的图像
cv2.imshow('Opening Image', opening_image)
# *显示最大连通域后的图像
cv2.imshow('Max Connected Domain Image', max_connected_domain_image)
# *显示闭操作后的图像
cv2.imshow('Closing Image', closing_image)
# *显示孔洞填充后的图像
cv2.imshow('Filled Image', filled_image)
# *显示轮廓图像
cv2.imshow('Contour Image', contour_image)

# cv2.imshow('Gray Image (Manual)', gray_image_manual)

cv2.waitKey(0)
cv2.destroyAllWindows()