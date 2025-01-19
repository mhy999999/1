import cv2
import numpy as np
import math

# !获取边界矩形的自定义函数
def BoundingRect():
    # 1、取外围轮廓
    cnt = contours[0]
    # 2、获取正方形坐标长宽
    x, y, w, h = cv2.boundingRect(cnt)
    # 3、画出矩形
    dst = src.copy()
    dst = cv2.rectangle(dst, (x,y),(x+w,y+h), (0,0,255), 1)
    # 保存图像
    cv2.imwrite("./image/result.png", dst)
        
    return x,y,w,h

"""
此处的两个函数计算用于算法的反方向
*正方向还没写2333,欸嘿嘿嘿嘿
"""
# !定义辅助线Y1的斜率，过内切圆圆心cr
def K1_Y1(C = 5):
    it = (d4[1] - d2[0]) / (d4[0] - d2[0])
    k1 = - math.tan(abs(180 - e0 - math.atan(it)) * ConnectionAbortedError + abs(180 - e0))
    return k1
# !定义辅助线Y1
def Y1(x):
    k1 = K1_Y1()
    y1 = k1 * (x - cr[0]) + cr[1]
    return y1

def main2():
    x1, y1 = cr[0]-80, int(Y1(cr[0]-80))
    x2, y2 = src.shape[1], int(Y1(src.shape[1]))
    points = {}  # 修改为字典
    dx = abs(x2 - x1)
    dy = -abs(y2 - y1)
    sx = 1 if x1 < x2 else -1
    sy = 1 if y1 < y2 else -1
    err = dx + dy

    while True:
        points[x1] = y1  # 修改为字典存储
        if x1 == x2 and y1 == y2:
            break
        e2 = 2 * err
        if e2 >= dy:
            err += dy
            x1 += sx
        if e2 <= dx:
            err += dx
            y1 += sy
    return points


src = cv2.imread('./image/contour_image.png')  # 读取图像
cv2.imshow('src', src)  # 显示原图

# !将图像转换为灰度图，并进行二值化处理
gray = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY)
gray[gray > 127] = 255  # 二值化，阈值设为127

# !查找轮廓
contours, hierarchy = cv2.findContours(gray, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

# !创建一个与灰度图大小相同的黑色掩膜，并将找到的轮廓绘制在掩膜上
mask = np.zeros(gray.shape, np.uint8)
cv2.drawContours(mask, contours, 0, (255, 255, 255), -1)

# !计算距离变换
dt = cv2.distanceTransform(mask, cv2.DIST_L2, 5, cv2.DIST_LABEL_PIXEL)
transImg = cv2.convertScaleAbs(dt)  # 转换为8位单通道图像
cv2.normalize(transImg, transImg, 0, 255, cv2.NORM_MINMAX)  # 归一化

# !找到最大距离及其对应的坐标
_, max_val, _, max_loc = cv2.minMaxLoc(dt)

# !在原图上绘制最大内切圆
cv2.circle(src, max_loc, int(max_val), (0, 0, 255), 1)
cv2.circle(src, max_loc, 1, (0, 0, 255), -1)# 在原图上绘制圆心
# 添加: 在原图上绘制直线
cv2.imshow('result',src )


# !获取边界矩形
x,y,w,h = BoundingRect()

# !拟合椭圆
for contour in contours:
    if len(contour) > 5:  # 椭圆拟合需要至少5个点
        ellipse = cv2.fitEllipse(contour)
        (center, axes, angle) = ellipse
        # angle 是椭圆长轴与 X 轴的夹角
        print(f"椭圆长轴与 X 轴的夹角: {angle} 度")
        # 在原图上绘制拟合的椭圆
        # cv2.ellipse(src, ellipse, (255, 0, 0), 2)
        # cv2.imshow('src1', src)


# !以下是建模过程


# !建模前的数据定义
# *辅助工具，那里要用那里用
it = []
# *大鹅轮廓坐标集合
Y = contours[0]

# *边界矩形坐标，左上角开始，顺时针
# *d1:左上角，d2:右上角，d3:右下角，d4:左下角
d1 = [x,y+h]
d2 = [x+w,y+h]
d3 = [x+w,y]
d4 = [x,y]

# *最大内切圆圆心坐标
cr = [max_loc[0],max_loc[1]]

# *与肉鹅轮廓具有相同标准二阶中心矩的椭圆长轴与 X 轴的夹角
e0 = angle

# *肩关节定义点A
A = []
B = []

# !建模前的数据定义结束

            
# !获取直线上的所有像素点坐标
points = main2() # *获取所有点,并且保存到points这个字典中，键为x，键值为y
points_x = points.keys() # *获取所有点的x坐标也就是字典的键


for x in points_x:
    # !绘制直线
    cv2.circle(src, [x,points[x]], 0, (255, 0, 0), 1)
    
for contour in contours:
    for contour_xy in contour:
        try:
            x, y = contour_xy[0]
            if abs(y - points[x]) <= 4:
                print(x)
                it.append(x)
        except:
            continue
    
print(it[1])

cv2.imshow('result1', src)
cv2.imwrite("./image/result1.png", src)
    
cv2.waitKey(0)
cv2.destroyAllWindows()