package com.konggit.appmvpdaggerted.app;

import android.app.Application;
import android.content.Context;

import com.konggit.appmvpdaggerted.dependenciesInjection.components.ITalksTEDAppComponent;
import com.konggit.appmvpdaggerted.dependenciesInjection.modules.TalksTEDAppModule;

/**
 * Created by erik on 07.01.2018.
 */

public class TalksTEDApp extends Application {

    private ITalksTEDAppComponent appComponent;

    public static TalksTEDApp get(Context context) {

        return (TalksTEDApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {

        super.onCreate();
        buildGraphAndInject();
    }

    public ITalksTEDAppComponent getAppComponent() {

        return appComponent;
    }

    //Creating the Object Graph
    //The object graph is the place where all these dependencies live.
    //The object graph contains the created instances and is able to inject them to the objects we add to it.
    public void buildGraphAndInject() {

        appComponent = DaggerITalksTEDAppComponent.builder().talksTEDAppModule(new TalksTEDAppModule(this)).build();
        appComponent.inject(this);
    }

}
//Dagger -Antonio Leiva
//What is a dependency?
//If we want to inject dependencies, we first need to know what a dependency is. In short,
// a dependency is a coupling between two modules of our code (in oriented object languages,
// two classes), usually because one of them uses the other to do something.
//
//        Why dependencies are dangerous?
//        Dependencies from high to low level are dangerous because we couple both modules in a way
// that if we need to change one module with another, we necessarily need to modify the code of
// the coupled module. That’s really bad if we want to create a testable app, because unit testing
// requires that when we are testing a module, it is isolated from the rest of modules in our app.
// To do this, we need to substitute dependencies with mocks. Imagine a code like this:
//
//        1
//public class Module1{
//2
//    private Module2 module2;
//3
//
//        4
//    public Module1(){
//        5
//        module2 = new Module2();
//        6
//    }
//7
//
//        8
//    public void doSomething(){
//        9
//      ...
//        10
//        module2.doSomethingElse();
//        11
//      ...
//        12
//    }
//13
//}
//How do we test ‘doSomething’ without testing ‘doSomethingElse’? If test fails, what method is the
// one that is failing? We can’t know. And things get worse if this ‘doSomethingElse’ method saves
// something in database or performs an API call.
//
//        Every ‘new’ we write is a hard dependency we probably need to avoid. And no, writing less
// modules isn’t a solution, don’t forget single responsibility principle.
//
//        How to solve it? Dependency inversion
//        If we can’t instantiate modules inside another module, we need to provide those modules
// in another way. Can you imagine how? Exactly, via constructor. That is basically what dependency
// inversion principle means. You shouldn’t rely on concrete module objects, only on abstractions.
//
//        Our previous example code would be something like this:
//
//        1
//public class Module1{
//2
//    private Module2 module2;
//3
//
//        4
//    public Module1(Module2 module2){
//        5
//        this.module2 = module2
//        6
//    }
//7
//
//        8
//    public void doSomething(){
//        9
//      ...
//        10
//        module2.doSomethingElse();
//        11
//      ...
//        12
//    }
//13
//}
//    So what’s dependency injection?
//        You already know! It consists of passing dependencies (inject them) via constructor
// in order to extract the task of creating modules out from other modules. Objects are instantiated
// somewhere else and passed as constructor attributes when creating the current object.
//
//        But here it comes a new problem. If we can’t create modules inside modules, there must
// be a place where those modules are instantiated. Besides, if we need to create modules with
// huge constructors including lots of dependencies, code will become dirty and hard to read,
// with objects travelling around the inmensity of our app. That’s what a dependency injector solves.
//
//        What is a dependency injector?
//        We can consider it as another module in our app that is in charge of providing instances
// of the rest of modules and inject their dependencies. That is basically its duty. The creation
// of modules is localized in a single point in our app, and we have full control over it.
//
//        And finally… What is Dagger?
//        Dagger is a dependency injector designed for low-end devices. Most dependency injectors
// rely on reflection to create and inject dependencies. Reflection is awesome, but is very time
// consuming on low-end devices, and specially on old android versions. Dagger, however, uses a
// pre-compiler that creates all the classes it needs to work. That way, no reflection is needed.
// Dagger is less powerful than other dependency injectors, but it’s the most efficient.
//    Include Dagger
//    into your project
//        There are two libraries that must be added if you want to use Dagger:
//
//        1
//        dependencies{
//        2
//        compile fileTree(dir:'libs',include:['*.jar'])
//        3
//        compile'com.squareup.dagger:dagger:1.2.+'
//        4
//        provided'com.squareup.dagger:dagger-compiler:1.2.+'
//        5
//        }
//        First one is Dagger library.Second library is the dagger compiler.It will create required
// classes in order to be able to inject dependencies.That’s the way it can avoid most reflection,
// by creating precompiled classes.As we only need it to compile the project,and won’t be used by
// application,we mark it as provided so that it isn’t included in final apk.
//
//        Creating your first module
//        Modules will be your daily job with dagger,so you need to feel comfortable with them.
// Modules are classes that provide instances of the objects we will need to inject.They are
// defined by annotating the
//
//class with
//        @Module.There
//        are some
//        extra parameters
//        that may
//        be configured, but I’ll explain when we use them.
//
//        Create a
//
//class called AppModule,that will provide, for example,the Application Context.It’s usually
// interesting to have an easy access to it.I created App,which extends from Application,and
// added to the manifest.
//
//        1
//@Module(
//        2
//        injects= {
//        3
//        App.class
//        4
//}
//        5
//)
//6
//
//public class AppModule {
//7
//
//        8
//    private App app;
//9
//
//        10
//
//    public AppModule(App app) {
//        11
//        this.app = app;
//        12
//    }
//13
//
//        14
//
//    @Provides
//    @Singleton
//    public Context provideApplicationContext() {
//        15
//        return app;
//        16
//    }
//17
//}
//    What is new here?
//
//@Module :identify this
//
//class as a Dagger module.
//        injects:Classes where this module is going to inject any of its dependencies.
// We need to specify those classes that are directly injected into object graph.T
// his will be covered soon.
//@Provides:Identify method as an injection provider.The name of the method doesn’t matter,it only
// relies on what
//
//class type is provided.
//@Singleton :if it’s present,the method will return always the same instance of the object,
// which is far better than regular singletons.If not,everytime this type is injected,we’ll get
// a new instance.In this case,as we are not creating a new instance,but returning an existing
// one,it would be the same if we don’t annotate as singleton,but it explains better what the
// provider is doing.Application instance is unique.
//
//        Why regular singletons are evil
//        Singletons are probably the most dangerous dependencies a project can have.First of
// all,because due to the fact that we are not creating an instance,it’s really hard to know where
// we are using it,so these are “hidden dependencies”.On the other way,we have no way to mock them
// for testing or to substitute it with another module,so our code becomes hard to maintain,to
// test and to evolve.Injected singletons,on the other way,have the benefits of a singleton(a unique
// instance)and,as we can create new instances at any moment,it’s easier to mock and to substitute
// with another piece of code,by subclassing or making them implement a common interface.
//
//        We will be creating another module in a new package called domain.It’s quite useful
// to have(at least)a module in every architecture layer.This module will provide an analytics
// manager,that will throw an event when the app starts,only by showing a Toast.In a real project,
// this manager could call any analytics service such as Google Analytics.
//
//        1
//@Module(
//        2
//        complete= false,
//        3
//        library= true
//        4
//)
//5
//
//public class DomainModule {
//6
//
//        7
//
//    @Provides
//    @Singleton
//    public AnalyticsManager provideAnalyticsManager(Application app) {
//        8
//        return new AnalyticsManager(app);
//        9
//    }
//10
//
//        11
//}
//    By identifying this module as not complete,we say that some of the dependencies in this module
// need to be provided by another module.That’s the case of Application,which comes from AppModule.
// When we require this AnalyticsManager from a dependency injection,dagger will use this method,
// and will detect that it needs another dependency,Application,which will be requested to the
// object graph(almost there!).We also need to specify this module as a library,because dagger
// compiler will detect that AnalyticsManager is not been used by itself or its injected classes.
// It’s acting as a library module for AppModule.
//
//        We will specify that AppModule will include this one,so back to previous class:
//
//        1
//@Module(
//        2
//        injects= {
//        3
//        App.class
//        4
//},
//        5
//        includes= {
//        6
//        DomainModule.class
//        7
//}
//        8
//)
//9
//
//public class AppModule {
//10
//        ...
//        11
//}
//    includes attribute
//    is there for that purpose.
//
//        Creating the Object Graph
//        The object graph is the place where all these dependencies live.The object graph contains
// the created instances and is able to inject them to the objects we add to it.
//
//        In previous examples(AnalyticsManager)we have seen the “classic” dependency injection,
// where injections are passed via constructor.But we have some classes in Android(Application,
// Activity)where we don’t have control over constructor,so we simply need another way to inject
// its dependencies.
//
//        The combination of ObjectGraph creation and this direct injection is represented in
// App class.The main object graph is created in the Application
//
//class and it is injected in order to get its dependencies.
//
//        1
//
//public class App extends Application {
//2
//
//        3
//    private ObjectGraph objectGraph;
//4
//    @Inject
//    AnalyticsManager analyticsManager;
//5
//
//        6
//
//    @Override
//    public void onCreate() {
//        7
//        super.onCreate();
//        8
//        objectGraph = ObjectGraph.create(getModules().toArray());
//        9
//        objectGraph.inject(this);
//        10
//        analyticsManager.registerAppEnter();
//        11
//    }
//12
//
//        13
//
//    private List<Object> getModules() {
//        14
//        return Arrays.<Object>asList(new AppModule(this));
//        15
//    }
//16
//}
//We specify dependencies by annotating them with @Inject. These fields must be public or default
// scoped, so that dagger can assign them. We create an array with our modules (we have only one,
// DomainModule is included in AppModule), and create an ObjectGraph with it. After it, we inject
// App instance manually. After that call, dependencies are injected, so we can call
// AnalyticsManager method.
If you’ve been following this series, you already read about dependency injection basics on part 1
//        and Dagger fundamentals on part 2. This last part about Dagger and dependency injection
//        Android is focused on scoped object graphs.
//
//        What’s the use of scoped graphs in Dagger?
//        When we instantiate Dagger singletons in application object graph, they live in memory until
//        the app is destroyed. But there are some singleton dependencies that are useful only when another object is alive. A simple example is a view and its presenter. Most of times you will only use a presenter with an activity at the same time. In MVP, a presenter without a view is useless. We don’t need to keep it in memory after the activity is destroyed.
//
//        How to create a scoped graph?
//        It’s easy, we will add it to the application graph. I’m creating a module per activity. So
//        let’s see an example in LoginActivity:
//
//        1
//@Module(
//        2
//        injects = LoginActivity.class,
//        3
//        addsTo = AppModule.class
//        4
//)
//5
//public class LoginModule {
//6
//
//        7
//    private LoginView view;
//8
//
//        9
//    public LoginModule(LoginView view) {
//        10
//        this.view = view;
//        11
//    }
//12
//
//        13
//    @Provides @Singleton public LoginView provideView() {
//        14
//        return view;
//        15
//    }
//16
//
//        17
//    @Provides @Singleton
//18
//    public LoginPresenter providePresenter(LoginView loginView, LoginInteractor loginInteractor) {
//        19
//        return new LoginPresenterImpl(loginView, loginInteractor);
//        20
//    }
//21
//}
//    This module injects LoginActivity, because it needs to inject the presenter to the activity
//        directly, not via constructor. It will be added to AppModule when be create the activity
//        graph. Both things must be declared at @Module annotation.
//
//        As you can see, I added a new dependency to LoginPresenter. Its LoginInteractor will
//        be injected from a new module called InteractorsModule. It’s a simple and standard module:
//
//        1
//@Module(
//        2
//        library = true
//        3
//)
//4
//public class InteractorsModule {
//5
//
//        6
//    @Provides public FindItemsInteractor provideFindItemsInteractor() {
//        7
//        return new FindItemsInteractorImpl();
//        8
//    }
//9
//
//        10
//    @Provides public LoginInteractor provideLoginInteractor() {
//        11
//        return new LoginInteractorImpl();
//        12
//    }
//13
//}
//    Add it to AppModule as you previously did with DomainModule:
//
//        1
//@Module(
//        2
//        injects = {
//        3
//        App.class
//        4
//},
//        5
//        includes = {
//        6
//        DomainModule.class,
//        7
//        InteractorsModule.class
//        8
//}
//        9
//)
//10
//public class AppModule {
//11
//        ...
//        12
//}
//Let’s create the object graph. It will be created by calling plus() to application graph and passing
//        new modules. So I created this method in App:
//
//        1
//public ObjectGraph createScopedGraph(Object... modules) {
//        2
//        return objectGraph.plus(modules);
//        3
//        }
//        And now in Activity we inject the presenter instead of instantiating it, create the graph
//        and inject the activity:
//
//        1
//public class LoginActivity extends Activity implements LoginView, View.OnClickListener {
//2
//
//        3
//    @Inject LoginPresenter presenter;
//4
//
//        5
//        ...
//        6
//
//        7
//    private ObjectGraph activityGraph;
//8
//
//        9
//    @Override
//10
//    protected void onCreate(Bundle savedInstanceState) {
//        11
//        super.onCreate(savedInstanceState);
//        12
//        setContentView(R.layout.activity_login);
//        13
//
//        14
//        ...
//        15
//
//        16
//        activityGraph = ((App) getApplication()).createScopedGraph(new LoginModule(this));
//        17
//        activityGraph.inject(this);
//        18
//    }
//19
//
//        20
//    @Override protected void onDestroy() {
//        21
//        super.onDestroy();
//        22
//        activityGraph = null;
//        23
//    }
//24
//
//        25
//        ...
//        26
//}
//    Set it to null in onDestroy so that it will be freed by garbage collector as soon as possible.
//        Now you will have presenter, view and model injected using Dagger.
//
//        Conclusion
//        Dagger is a powerful tool, but its learning curve is larger than other libraries because
//        it’s important that main concepts are embraced and that you understand why it is useful.
//        I hope this set of articles helped you resolve most of difficulties you may find at the
//        beginning.
//
//        I updated the code and created a BaseActivity that encapsulates the repetitive work of
//        instantiating the scoped graph.