## Генетический алгоритм

Эвристический алгоритм поиска, используемый для решения задач оптимизации и моделирования путём случайного подбора, комбинирования и вариации искомых параметров с использованием механизмов, аналогичных естественному отбору в природе. Является разновидностью эволюционных вычислений, с помощью которых решаются оптимизационные задачи с использованием методов естественной эволюции, таких как наследование, мутации, отбор и кроссинговер. Отличительной особенностью генетического алгоритма является акцент на использование оператора «скрещивания», который производит операцию рекомбинации решений-кандидатов, роль которой аналогична роли скрещивания в живой природе.

<img style="display: block; margin-left: auto; margin-right: auto" alt="system schema" src="https://upload.wikimedia.org/wikipedia/commons/c/ca/Schema_simple_algorithme_genetique_ru.png"/>

### Основные классы

- `Chromosome` - реализация хромосомы, включающая набор геномов, функции мутации и скрещивания 
- `Population` - реализация популяции индивидов
- `Population config` - настройки популяции

### Пример использования
```java
class Example {
    public static void main(String[] args) {
        PopulationConfig config = PopulationConfig.builder()
                .populationSize(1000)
                .hallOfFameSize(50)
                .mutationProbability(0.5)
                .crossingProbability(0.7)
                .generationCount(1000)
                .build();

        ChromosomeFloat min = ChromosomeFloat.create(minArray);
        ChromosomeFloat max = ChromosomeFloat.create(maxArray);

        Population<ChromosomeFloat> population = Population.createRandom(min, max, config,
                (localMin, localMax) -> ChromosomeFloat.create(min, max));

        population.run(genome -> fitFunction(Arrays.stream(genome.getGenes())));
        
        Set<ChromosomeFloat> individuals = population.getIndividuals();
    }
}
```

### Полезные ссылки
- https://proproprogs.ru/ga - общие понятия о концепции и код на Python


## Роевой алгоритм

Интеллект роя относится к коллективному разуму. Биологи и естествоиспытатели изучают поведение общественных насекомых из-за их эффективности в решении сложных задач, таких как поиск кратчайшего пути между своим гнездом и источником пищи или организация своих гнезд. Несмотря на то, что по отдельности эти насекомые неискушенны, в рое они творят чудеса, взаимодействуя друг с другом и окружающей средой. За последние два десятилетия поведение различных стай, которые используются для поиска добычи или спаривания, было смоделировано с помощью метода численной оптимизации. В этой главе кратко описаны восемь различных алгоритмов, основанных на роевом интеллекте, и перечислены этапы их работы. К таким методам относятся оптимизатор колонии муравьев, оптимизатор роя частиц, алгоритм искусственной колонии пчел, алгоритм светлячка, алгоритм светлячка, алгоритм поиска кукушки, алгоритм летучей мыши и алгоритм поиска охоты. Две задачи оптимизации, взятые из литературы, решаются всеми этими восемью алгоритмами и сравнивается их производительность. Замечено, что большинство алгоритмов, основанных на роевом интеллекте, представляют собой простые и надежные методы, которые эффективно определяют оптимальное решение задач оптимизации, не требуя больших математических усилий.

<img style="display: block; margin-left: auto; margin-right: auto" alt="system schema" src="https://media.springernature.com/lw685/springer-static/image/art%3A10.1007%2Fs41109-020-00260-8/MediaObjects/41109_2020_260_Fig3_HTML.png"/>

### Пример использования
```java
class Example {
    public static void main(String[] args) {
        Random random = new Random();
        ParticleFloat max = new ParticleFloat(new Double[]{6.0, 6.0}, new Double[]{0.0, 0.0});
        ParticleFloat min = new ParticleFloat(new Double[]{-6.0, -6.0}, new Double[]{0.0, 0.0});


        Supplier<ParticleFloat> create = () -> {
            Double[] initialParticlePosition = {
                    random.nextDouble() * (max.getPosition()[0] - min.getPosition()[0]) + min.getPosition()[0],
                    random.nextDouble() * (max.getPosition()[1] - min.getPosition()[1]) + min.getPosition()[1],
            };
            Double[] initialParticleSpeed = {
                    random.nextDouble() * (max.getPosition()[0] - min.getPosition()[0]) + min.getPosition()[0],
                    random.nextDouble() * (max.getPosition()[1] - min.getPosition()[1]) + min.getPosition()[1],
            };
            return new ParticleFloat(initialParticlePosition, initialParticleSpeed);
        };
        FitnessFunction fitnessFunction = this::getHimmelblau;

        Multiswarm<ParticleFloat> multiswarm = Multiswarm.create(10, 10, fitnessFunction, create, min, max);

        for (int i = 0; i < 500; i++) {
            multiswarm.mainLoop();
        }
    }
}
```

### Полезные ссылки
- https://towardsdatascience.com/swarm-intelligence-coding-and-visualising-particle-swarm-optimisation-in-python-253e1bd00772 - описание алгоритма и код на Python