(ns rays.materials-test
  (:require [clojure.test :refer [deftest testing is]]
            [rays.materials :as sut]
            [rays.colors :as c]
            [rays.common :refer [eq-floats?]]))

(deftest test-materials
  (testing "the default material"
    (let [m (sut/material)]
      (is (c/equal? (sut/color m) c/white))
      (is (eq-floats? (sut/ambient m) 0.1))
      (is (eq-floats? (sut/diffuse m) 0.9))
      (is (eq-floats? (sut/specular m) 0.9))
      (is (eq-floats? (sut/shininess m) 200.0)))))
