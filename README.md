# Frontend 설정

### 노드 패키지 설치하기 ( package.json 참조 )
터미널에서 frontend 폴더 이동 후 아래의 명령어 실행
```
npm install
```

### 로컬환경에서 frontend 실행하기기
터미널에서 frontend 폴더 이동 후 아래의 명령어 실행
```
npm run local
```

# Backend 설정

### java runtime 설정하기

 settings.json에 java.configuration.runtimes 설정하기
1. 설정 페이지 이동
ctrl + , 또는 파일 > 기본설정 > 설정 이동
2. 설정 페이지에서 javahome 검색
3. java > jdt > ls > java:home에서 settings.json에서 편집 클릭
4. settings.json에 아래와 같이 추가
  "java.configuration.runtimes": [
    {
        "name": "JavaSE-1.8",
        "path": "java 1.8 경로",
        "default": true
    },
    {
        "name": "JavaSE-11",
        "path": "java 11 경로"
    },
    {
        "name": "JavaSE-17",
        "path": "java 17 경로"
    }
  ]

## 문제 해결
1. The project was not built since its build path is incomplete
settings.json에서 java.configuration.runtimes 설정 후 ctrl + shift + p 누른 후 나오는 입력창에서 java clean 검색
java:Clean Java Language Server Workspace 선택 후 재시작

2. npm install시 could not resolve 에러 발생
 예시)  While resolving: @vue/eslint-config-standard@6.1.0
        Found: eslint-plugin-vue@8.7.1
        node_modules/eslint-plugin-vue
          dev eslint-plugin-vue@"^8.0.3" from the root project
npm 7 버전부터 peer dependency conflict가 있는 경우 npm install를 중단시키기 때문에 npm install 뒤에 --force나 --legacy-peer-deps 옵션을 사용해야 한다.
--force를 하면 package-lock.json에 몇가지의 다른 의존 버전들을 추가한다.
--legacy를 하면 peerDependency가 맞지 않아도 일단 설치한다.

3. Parsing error: No Babel config file detected for [프로젝트 경로]\src\App.vue. Either disable config file checking with requireConfigFile: false, or configure Babel so that it can find the config files.
settings.json에 "eslint.workingDirectories": [{ "mode": "auto"}] 추가