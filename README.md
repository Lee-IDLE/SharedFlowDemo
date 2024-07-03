SharedFlow를 공부하기 위한 데모 프로젝트입니다.

1. MutableSharedFlow<Int>로 ShareFlow 객체 생성
2. asSharedFlow를 사용해 위에 생성한 객체의 읽기 전용으로 객체 하나 더 생성
3. viewModelScope를 사용해 비동기로 값을 생성해 _sharedFlow.emit으로 값을 방출한다.
4. LaunchedEffect를 사용해 비동기적으로 UI에서 값을 수집
5. LifeCycleOwner.current.repeatOnLifecycle을 사용해 원하는 때에만 수집을 하도록 함(여기서는 Lifecycle이 Started 상황일때만 하도록 함
