import unittest
import pandas as pd
import json
from io import StringIO
from src.utils.data_manager import DataManager
from src.model.Point import Point
from src.model.Trajectoire import Trajectoire

class TestDataManager(unittest.TestCase):

    def setUp(self):
        """Crée un DataFrame fictif pour tester."""
        csv_data = """tick,x0,y0,vec_x0,vec_y0
        1,1,2,0.5,-0.5
        2,3,4,0.7,-0.3
        3,5,6,0.9,-0.1"""
        self.df = pd.read_csv(StringIO(csv_data))

    def test_find_min_max(self):
        """Teste le calcul des valeurs min et max."""
        min_max_values = DataManager._find_min_max(self.df)
        self.assertEqual(min_max_values['min_x'], 1)
        self.assertEqual(min_max_values['max_x'], 5)
        self.assertEqual(min_max_values['min_y'], 2)
        self.assertEqual(min_max_values['max_y'], 6)
        self.assertEqual(min_max_values['min_vec_x'], 0.5)
        self.assertEqual(min_max_values['max_vec_x'], 0.9)
        self.assertEqual(min_max_values['min_vec_y'], -0.5)
        self.assertEqual(min_max_values['max_vec_y'], -0.1)

    def test_create_point(self):
        """Teste la création d'un point normalisé."""
        min_max_values = DataManager._find_min_max(self.df)
        point = DataManager._create_point(3, 4, 0.7, -0.3, 2, min_max_values)
        self.assertIsInstance(point, Point)
        self.assertAlmostEqual(point.tick, 2)

    def test_load_data_from_csv(self):
        """Teste le chargement des trajectoires depuis un CSV."""
        with StringIO("""tick,x0,y0,vec_x0,vec_y0
        1,1,2,0.5,-0.5
        2,3,4,0.7,-0.3
        3,5,6,0.9,-0.1""") as csv_file:
            trajs = DataManager.load_data_from_csv(csv_file)
            self.assertEqual(len(trajs), 10)
            self.assertIsInstance(trajs[0], Trajectoire)
            self.assertGreater(len(trajs[0].points), 0)

    def test_export_and_load_json(self):
        """Teste l'export et l'import des trajectoires en JSON."""
        trajectoires = [Trajectoire(0, [Point(100, 200, 1), Point(300, 400, 2)])]
        json_file = "test_trajectoires.json"
        
        DataManager.export_trajectories_to_json(trajectoires, json_file)
        loaded_trajs = DataManager.load_traj_from_json(json_file)
        
        self.assertEqual(len(loaded_trajs), 1)
        self.assertEqual(loaded_trajs[0].player_id, 0)
        self.assertEqual(len(loaded_trajs[0].points), 2)
        self.assertAlmostEqual(loaded_trajs[0].points[0].x, 100)
        self.assertAlmostEqual(loaded_trajs[0].points[0].y, 200)
        self.assertEqual(loaded_trajs[0].points[0].tick, 1)

if __name__ == '__main__':
    unittest.main()
