import cv2
import numpy as np


def grain(mat):
    mat = mat.astype(np.float32) / 255

    return mat * 255


if __name__ == '__main__':
    mat = cv2.imread('images/orange_cat.png')
    result = grain(mat)
    cv2.imwrite("result_images/grained_result.png", result)
