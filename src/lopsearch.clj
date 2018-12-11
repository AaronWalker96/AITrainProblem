(require 'search.opsearch)

;;;;;;;;;;;;;
;;  @lad   ;;
;;;;;;;;;;;;;
;
; (def start-state
;   '#{(isa trainOne Train)
;      (tas trainOne stationOne)
;      (cas cargoOne stationTwo)})

(def lops
  '{load {:pre ((train ?train)
                (carries ?train nil)
                (tat ?train ?stat)
                (cat ?cargo  ?stat))
          :add ((carries ?train ?cargo))
          :del ((cat ?cargo ?stat)
                (carries ?train nil))
          :txt (?train loads ?cargo at ?stat)
          :cmd [load ?cargo]}

    unload {:pre  ((tat ?train ?stat)
                   (carries ?train ?cargo))
            :add ((carries ?train nil)
                  (cat ?cargo  ?stat))
            :del ((carries ?train ?cargo))
            :txt (?train unloads ?cargo at ?stat)
            :cmd [unload ?cargo]}

    move    {:pre ((train ?train)
                   (tat ?train ?stat)
                   (connects ?stat ?dest))
             :add ((tat ?train ?dest))
             :del ((tat ?train ?stat))
             :txt (?train moves from ?stat to ?dest)
             :cmd [move ?dest]}})

(def lworld
  '#{(train t1)
     (cargo c1)
     (stati s1)
     (stati s2)
     (connects s1 s2)
     (connects s2 s1)})


(def lstate1
  '#{(tas t1 s1)
     (carries t1 nil)
     (cas c1 s2)})