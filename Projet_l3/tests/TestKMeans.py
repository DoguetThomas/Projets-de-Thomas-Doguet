import unittest
import numpy as np
from src.algorithms.KMeans import KMeans

class TestKMeans(unittest.TestCase):
    def setUp(self):
        """Initialisation des données pour les tests."""
        self.data = np.array([
            [1, 2, 0], [2, 3, 1], [3, 3, 2],
            [8, 8, 0], [9, 9, 1], [10, 10, 2],
            [15, 15, 0], [16, 16, 1], [17, 17, 2]
        ])
        self.kmeans = KMeans(k=3, max_iters=10, time_weight=1.0, tol=1e-4)

    def test_initialization(self):
        """Test de l'initialisation du modèle."""
        self.assertEqual(self.kmeans.k, 3)
        self.assertEqual(self.kmeans.max_iters, 10)
        self.assertEqual(self.kmeans.time_weight, 1.0)
        self.assertAlmostEqual(self.kmeans.tol, 1e-4)
        self.assertIsNone(self.kmeans.centroids)
        self.assertEqual(self.kmeans.clusters, [])

    def test_fit(self):
        """Test de l'entraînement du modèle."""
        clusters = self.kmeans.fit(self.data)
        self.assertEqual(len(clusters), 3)
        self.assertEqual(len(self.kmeans.centroids), 3)
        
        for cluster in clusters:
            self.assertIsInstance(cluster, list)
            for point in cluster:
                self.assertEqual(len(point), 3)  # (x, y, tick)

    def test_predict(self):
        """Test de la prédiction de clusters pour de nouveaux points."""
        self.kmeans.fit(self.data)
        new_points = np.array([[2, 2, 1], [9, 9, 1], [16, 16, 2]])
        predictions = self.kmeans.predict(new_points)
        
        self.assertEqual(len(predictions), len(new_points))
        for pred in predictions:
            self.assertTrue(0 <= pred < self.kmeans.k)

    def test_prepare_data_for_prefixspan(self):
        """Test de la préparation des données pour PrefixSpan."""
        self.kmeans.fit(self.data)
        sequences = self.kmeans.prepare_data_for_prefixspan()
        
        self.assertEqual(len(sequences), 3)
        for sequence in sequences:
            self.assertIsInstance(sequence, list)
            for point in sequence:
                self.assertIsInstance(point, tuple)
                self.assertEqual(len(point), 2)  # (x, y)

if __name__ == '__main__':
    unittest.main()