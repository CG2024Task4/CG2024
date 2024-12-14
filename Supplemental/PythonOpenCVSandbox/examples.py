import cv2
import numpy as np


def load_show_save_image():
    mat = cv2.imread('images/cat_icon.png')
    if mat is None:
        print("Ошибка загрузки изображения")
        return

    cv2.imshow('Image', mat)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

    if len(mat.shape) == 2:
        print("Одноканальное изображение.")
    elif len(mat.shape) == 3:
        channels = mat.shape[2]
        print(f"Изображение с {channels} каналами.")
    else:
        print("Неизвестный формат изображения.")

    cv2.imwrite("result_images/cat_icon.png", mat)


def convert_image_to_float():
    mat = cv2.imread('images/cat_icon.png')
    mat = mat.astype(np.float32) / 255.0
    mat = mat * 255
    cv2.imwrite("result_images/cat_icon.png", mat)


def convert_image_to_lab():
    mat = cv2.imread('images/cat_icon.png')
    mat = mat.astype(np.float32) / 255.0

    mat = cv2.cvtColor(mat, cv2.COLOR_BGR2Lab)
    lab_channels = cv2.split(mat)

    l_channel = 0.01 * lab_channels[0]
    a_channel = 0.01 * lab_channels[1]
    b_channel = 0.01 * lab_channels[2]

    # for row in range(l_channel.shape[0]):
    #     for col in range(l_channel.shape[1]):
    #         # l_channel[row, col] = 0.1
    #         pixel = l_channel[row, col]
    #         print(pixel)

    mat = cv2.merge([100 * l_channel, 100 * a_channel, 100 * b_channel])
    mat = cv2.cvtColor(mat, cv2.COLOR_Lab2BGR)
    mat = mat * 255.0
    cv2.imwrite("result_images/cat_icon.png", mat)


def random_one_channel_image():
    noise = cv2.UMat(np.zeros((100, 100, 1), dtype=np.float32))
    noise = cv2.randn(noise, 0.5, 0.25)
    noise = cv2.convertScaleAbs(noise, alpha=255.0)
    cv2.imwrite("result_images/noise.png", noise)


def blur_image():
    mat = cv2.imread('images/cat_icon.png')
    mat = cv2.GaussianBlur(mat, (25, 25), 0)
    cv2.imwrite("result_images/blured_cat_icon.png", mat)


def resize_image():
    mat = cv2.imread('images/cat_icon.png')
    mat = cv2.resize(mat, (100, 100), interpolation=cv2.INTER_LINEAR)
    cv2.imwrite("result_images/resized_cat_icon.png", mat)


def merge_images():
    opacity = 0.5
    first_mat = cv2.imread('images/cat_icon.png')
    first_mat = cv2.resize(first_mat, (1000, 1000), interpolation=cv2.INTER_LINEAR)
    print(first_mat.shape)

    second_mat = cv2.imread('images/orange_cat.png')
    second_mat = cv2.resize(second_mat, (1000, 1000), interpolation=cv2.INTER_LINEAR)
    print(second_mat.shape)

    result_mat = cv2.addWeighted(first_mat, 1.0 - opacity, second_mat, opacity, 0.0)
    cv2.imwrite("result_images/merged_images.png", result_mat)


def pyramids():
    mat = cv2.imread('images/cat_icon.png')
    mat = cv2.pyrDown(mat)
    cv2.imwrite("result_images/cat_icon_pyr.png", mat)


if __name__ == '__main__':
    load_show_save_image()
    # convert_image_to_float()
    # convert_image_to_lab()
    # random_one_channel_image()
    # blur_image()
    # resize_image()
    # merge_images()
    # pyramids()
