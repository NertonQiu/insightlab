function initApp() {
  var component = pageInitializer();
  ACX({
    comboBase: '/insightlab/srv/yui/combo?'
  }).use(component.name,
    function (A) {
      console.log(component.name);
      if(component.name==="fileloader-page"){
        var globalnav = $("#acx-global-nav");
        console.log(globalnav);
        var tabs = $(globalnav).find("div.acx-topnav-tabs");
        $(tabs).find("a div.active").removeClass("active");
        var filediv = $(tabs).find("a div[title='FILE LOADER  - DEV']");
        $(filediv).addClass("active");
        var dataLoader=new DataLoader();
        dataLoader.loadData('/srv/authtoken/user/details/username', function (data) {
            console.log(data);
            if (data) {
              var username=null;
              var companyid=null;
              username = data['username'];
              var frame = document.getElementById("fileloader");
              dataLoader.loadData('/srv/authtoken/company/details/companyid', function (data) {
                  companyid=data['company_id'];
                  frame.src+="?userName="+username+"&companyId="+companyid;
              });
            } else {
                frame.src+="?userName=TestingWithoutSSO&companyId=00000000";
            }
        });
        return;
      }
      var page = new A[component.pageConstructor]('widget-content');
      page.init();
    });
}
var pageInitializer = (function(){
  var pagesConfig = {
    hash: window.location.hash,
    container: 'widget-content',
    insightlab: {
      name: 'home-page',
      title: '概览',
      pageConstructor: 'HomePage',
      redirect: {
        isActive: false,
        url: ''
      }
    },
    models: {
      name: 'model-page',
      title: '模型列表',
      pageConstructor: 'ModelPage',
      redirect: {
        isActive: false,
        url: ''
      }
    },
    fileloader: {
      name: 'fileloader-page',
      title: 'FileLoader',
      pageConstructor: 'FileLoaderPage',
      redirect: {
        isActive: false,
        url: ''
      }
    },
    scorecards: {
      name: 'scorecard-page',
      title: '打分详情',
      pageConstructor: 'Scorecard',
      redirect: {
        isActive: true,
        url: '../index.html'
      }
    },
    co: {
      name: 'co-page',
      title: 'COPage',
      pageConstructor: 'COPage',
      redirect: {
        isActive: false,
        url: ''
      }
    },
    'scorecard-details': {
      name: 'scorecard-details-page',
      title: 'Scorecard Details',
      pageConstructor: 'ScorecardDetails',
      redirect: {
        isActive: true,
        url: '../index.html'
      }
    },
    dpa: {
      name: 'profile-app-page',
      title: 'Audience Portrait',
      pageConstructor: 'ProfileAppPage',
      redirect: {
        isActive: true,
        url: '../index.html'
      }
    },
    insights: {
      name: 'insights-page',
      title: '受众文件',
      pageConstructor: 'InsightsPage',
      redirect: {
        isActive: false,
        url: ''
      }
    },
    personicx: {
      name: 'personicx-page',
      title: 'Personicx',
      pageConstructor: 'PersonicxPage',
      redirect: {
        isActive: true,
        url: '../index.html'
      }
    },
    'build-segment': {
      name: 'buildSegment-page',
      title: 'BuildSegmentPage',
      redirect: {
        isActive: false,
        url: ''
      }
    }
  };

  function initMenu_() {
    var $links = $('.acxiom-topnav-tabs a'),
      $li = $links.find('div'),
      url = window.location.href,
      index = 0,
      length = $links.length;
    $li.first().addClass('active');
    for(;index<length;index++){
      if (url.indexOf($links[index].getAttribute('href')) !== -1) {
        $li.removeClass('active');
        $($links[index]).find('div').addClass('active');
      }
    }
    $links.click(function () {
      if (url === $(this).attr('href')){
        return false;
      }
    });
  }

  function initPage(href) {
    GlobalNav.events.addListener('navClick', function (e) {
      if (window.location.href !== e.detail.url) {
        window.location.href = e.detail.url;
      }
    }, false);

    var url = [],
      currentPage;

    $.each(href.split('/'), function (key, value) {
      var val = value.toLowerCase();
      if (val !== '' && val !== 'index.html') {
        url.push(val);
      }
    });

    currentPage = url[url.length - 1];

      document.title = '安客诚安众通 | 模型 | ' + pagesConfig[currentPage].title;

    if (pagesConfig[currentPage].redirect.isActive && pagesConfig.hash.length <= 1) {
      window.location = pagesConfig[currentPage].redirect.url;
    }
    return pagesConfig[currentPage];
  }

  return function () {
    //initMenu_();
    var component = initPage(window.location.pathname);
    return component;
  };
})();
