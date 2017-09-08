# Predstavitev informacije z JMetal

Vektor (IntegerSolution) pri optimizaciji [C1,F1,T1,  C2,F2,T2,  ....,  CN,FN,TN,   V1,V2,Vk ] 

* C - Tip tovora (1-3)
* F - Začetno nakladalno mesto
* T - Končno nakladalno esto

* C1,F1,T1 - vozijo 1 v prvem ciklu
* C2,F2,T2 - vozijo 2 v prvem ciklu
* ...
* CN,FN,TN - vozijo k v zadnjem ciklu

* V1,V2,Vk - ali vozilo vozi v trenutnem sporedu (0,1)

Dolžina vektorja = 3 * št. vozil * št. ciklov + št. vozil

## Implementacija novega objectiva

Za dodajanje novega objectiva je potrebno implementirati interface IEvaluator in razred ob zagonu poslati SchedulingProblem.

**Primer:**
```java
public class TestEvaluator implements IEvaluator
{
    @Override
    public double evaluate(FullSchedule schedule)
    {
        double objective = //Izračun
        return return objective
    }
}
```

