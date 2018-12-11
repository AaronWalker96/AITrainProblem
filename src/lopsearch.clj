(require 'search.opsearch)

;;;;;;;;;;;;;
;;  @lad   ;;
;;;;;;;;;;;;;

(def lops
  '{load {:pre ((train ?train)
                (cargo ?cargo)
                (carries ?train nil)
                (tat ?train ?stat)
                (cat ?cargo  ?stat))
          :add ((carries ?train ?cargo))
          :del ((cat ?cargo ?stat)
                (carries ?train nil))
          :txt (?train loads ?cargo at ?stat)
          :cmd [load ?cargo]}

    unload {:pre  ((carries ?train ?cargo)
                   (tat ?train ?stat)
                   (:guard (? cargo)))
            :add ((carries ?train nil)
                  (cat ?cargo  ?stat))
            :del ((carries ?train ?cargo))
            :txt (?train unloads ?cargo at ?stat)
            :cmd [unload ?cargo]}
    
    move    {:pre ((train ?train)
                   (tat ?train ?stat)
                   (links ?stat ?dest))
             :add ((tat ?train ?dest))
             :del ((tat ?train ?stat))
             :txt (?train moves from ?stat to ?dest)
             :cmd [move ?dest]}})


; TEST 1
(def lworld1
  '#{(train t1)
     (cargo c1)
     (links s1 s2)
     (links s2 s1)})

(def lstate1
  '#{(tat t1 s1)
     (carries t1 nil)
     (cat c1 s2)})

;(ops-search lstate1 '((cat c1 s1) (cat c2 s2b) (cat c3 s1)) lops :world lworld1)


; TEST 2
(def lworld2
  '#{(train t1)
     (cargo c1)
     (links s1 s2)
     (links s2 s1)
     (links s2 s3)
     (links s3 s2)})

(def lstate2
  '#{(tat t1 s1)
     (carries t1 nil)
     (cat c1 s3)})

;(ops-search lstate2 '((cat c1 s1) (cat c2 s2b) (cat c3 s1)) lops :world lworld2)


; TEST 3
(def lworld3
  '#{(train t1)
     (train t2)
     (cargo c1)
     (cargo c2)
     (cargo c3)
     (links s1 s2a)
     (links s2a s1)
     (links s2a s2b)
     (links s2b s2a)
     (links s2a s3)
     (links s3 s2a)})

(def lstate3
  '#{(tat t1 s1)
     (tat t2 s3)
     (carries t1 nil)
     (carries t2 nil)
     (cat c1 s2b)
     (cat c2 s3)
     (cat c3 s3)})

; (ops-search lstate3 '((cat c1 s1) (cat c2 s2b) (cat c3 s1)) lops :world lworld3)


; TEST 4
(def lworld4
  '#{(train t1)
     (train t2)
     (cargo c1)
     (cargo c2)
     (cargo c3)
     (cargo c4)
     (links s1 s2a)
     (links s2a s1)
     (links s2a s2b)
     (links s2b s2a)
     (links s2a s3)
     (links s3 s2a)
     (links s3 s4a)
     (links s4a s3)
     (links s4a s4b)
     (links s4b s4a)
     (links s4a s5)
     (links s5 s4a)})

(def lstate4
  '#{(tat t1 s3)
     (tat t2 s5)
     (carries t1 nil)
     (carries t2 nil)
     (cat c1 s4b)
     (cat c2 s4b)
     (cat c3 s5)
     (cat c4 s5)})