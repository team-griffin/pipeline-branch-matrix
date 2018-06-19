// [
//   master: [
//     MY_ENV: 'value'
//   ]
// ]
def call(LinkedHashMap configs, Closure body) {
  script {
    def envs = [];

    for (Map.Entry<String, LinkedHashMap<String, String>> entry : configs.entrySet()) {
      String branch = entry.getKey();
      LinkedHashMap<String, String> config = entry.getValue();

      if (env.BRANCH_NAME != branch) {
        continue;
      }

      for (Map.Entry<String, String> configEntry : config.entrySet()) {
        String key = configEntry.getKey();
        String value = configEntry.getValue();

        envs.push(key + '=' + value);
      }
    }


    withEnv(envs) {
      body()
    } // withEnv
  } // script
}