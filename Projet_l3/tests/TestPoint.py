import numpy as np
import unittest

from src.model.Point import Point
from src.utils.data_manager import data_manager

class TestPoint(unittest.TestCase):
    def setUp(self):
        self.data_instance = Data(file_path=None)

        self.data_instance.min_x = 0
        self.data_instance.max_x = 100
        self.data_instance.min_y = 0
        self.data_instance.max_y = 100

        self.data_instance.min_vec_x = 0
        self.data_instance.min_vec_y = 10
        self.data_instance.max_vec_x = 0
        self.data_instance.max_vec_y = 10

    def test_get_coords_and_normalize(self):
        point = Point2D(50,50,5,5, self.data_instance)
        coord = point.get_coords()
        print("coordonnées des points : ", coord)
        self.assertTrue(0 <= coord[0] <= 1000)
        self.assertTrue(0 <= coord[1] <= 1000)
    
    

if __name__ == '__main__':
    unittest.main()