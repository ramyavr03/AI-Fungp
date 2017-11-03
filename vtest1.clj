(ns fungp.vtest1 
  (:use fungp.core) 
  (:use fungp.util) 
  (:use clojure.pprint)) 
(def sample-functions 
  '[[+ 2] 
    [- 2] 
    [* 2] 
    [fungp.util/sdiv 2]
    [fungp.util/abs 1]
    [fungp.util/sin 1]
    [inc 1]
    [dec 1]
   ]) 
(def sample-parameters 
    ['x]) 
(def number-literals 
  (map float (range 10))) 
(def in-list  '(-5.145, -0.438, -0.071, -0.058, -0.0080,-0.0037, -0.0015, -0.0001, 0.0003, 0.0021, 0.0055, 0.0337, 0.2224, 0.990, 7.211)) 
(def out-list '(0.997, 0.3864, 0.070, -0.058, 0.0076, 0.0031, -0.0014, 0.0002, -0.0003, 0.0021, -0.0052, -0.0330, -0.2171, 0.8647, 1.0182))
(defn sample-fitness 
  [tree] 
  (try 
    (let [f (compile-tree tree sample-parameters) 
          results (map f in-list)] 
      (reduce + (map off-by-sq out-list results))) 
    (catch Exception e (println e) (println tree)))) 
(defn sample-report 
  [tree fitness] 
  (pprint tree) 
  (println (str "Error:\t" fitness "\n")) 
  (flush)) 
(defn test-regression1 
  [n1 n2] 
  (println "\nfungp :: Functional Genetic Programming in Clojure") 
  (println "Mike Vollmer, 2012") 
  (println (str "Test inputs: " (vec in-list))) 
  (println (str "Test outputs: " (vec out-list))) 
  (println (str "Max generations: " (* n1 n2)))
  (println) 
  (let [options {:iterations n1 
                 :migrations n2 
                 :num-islands 6 
                 :population-size 40 
                 :tournament-size 5 
                 :mutation-probability 0.1 
                 :max-depth 10 
                 :terminals sample-parameters 
                 :numbers number-literals 
                 :fitness sample-fitness 
                 :functions sample-functions 
                 :report sample-report } 
        [tree score] (rest (run-genetic-programming options))] 
    (do (println "Done!") 
        (sample-report tree score)))) 

