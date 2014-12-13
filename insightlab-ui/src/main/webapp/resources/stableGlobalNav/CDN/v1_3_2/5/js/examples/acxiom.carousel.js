(function() {

  ACXM.Example.Carousel = Carousel;

  function Carousel() {
  }

  Carousel.init = function(_pages) {
    var options = _pages, currentPage, firstItemIndex = 0;

    var carousel_items_root = document.getElementById("carousel_items");
    var self = this;
    var carouselItems = [], loadedStyles = [], loadedScripts = [];

    for (var i = 0; i < options.length; i++) {
      var item = "<div class='scenario_items'><img src='" + options[i].icon + "'>";
      item += "<p class='carousel-item-title'>" + options[i].title + "</p></div>";
      $("#carousel_items").append(item);
    }

    if (carousel_items_root) {
      carouselItems = carousel_items_root.getElementsByTagName("div");
    }

    if (carouselItems.length > 7) {

      document.getElementById("moveRight").getElementsByTagName('img')[0].id = "";

      for (var i = 7; i < carouselItems.length; i++) {

        carouselItems[i].style.display = "none";
      }
    }

    this.moveRight = function() {

      if (firstItemIndex + 7 < carouselItems.length) {
        firstItemIndex++;
      } else {
        document.getElementById("moveRight").getElementsByTagName('img')[0].id = "moveRightcur";

      }
      document.getElementById("moveLeft").getElementsByTagName('img')[0].id = "";
      for (var i = firstItemIndex; i < firstItemIndex + 7; i++) {

        carouselItems[i].style.display = "table-cell";
      }
      carouselItems[firstItemIndex - 1].style.display = "none";
    };

    this.moveLeft = function() {

      if (firstItemIndex > 0) {
        firstItemIndex--;
      } else {
        document.getElementById("moveLeft").getElementsByTagName('img')[0].id = "moveLeftcur";

      }
      document.getElementById("moveRight").getElementsByTagName('img')[0].id = "";

      for (var i = firstItemIndex; i < firstItemIndex + 7; i++) {

        carouselItems[i].style.display = "table-cell";

      }
      carouselItems[firstItemIndex + 7].style.display = "none";
    },

      initializeListeners.call(this);

    function initializeListeners() {

      $("#carousel_items div").each(function(i) {

        var j = i;

        if (i == 0) {
          $(this).addClass("selected");
        }

        $(this).click(function() {

          loadPage(j);

          $("#carousel_items div").each(function() {
            $(this).removeClass("selected");
          });

          $(this).addClass("selected");
        });
      });

      if (carouselItems.length > 7) {

        $("#moveLeft").click(function() {
          self.moveLeft();
        });

        $("#moveRight").click(function() {
          self.moveRight();
        });
      }
    }

    loadPage(0);

    function loadPage(_currentPage) {

      var isContentLoaded = false;
      var isLoadScripts = true;


      currentPage = _currentPage;


      loadStyles();

      // load content
      $('#_content').load(options[currentPage].url + '  #content', function() {

        isContentLoaded = true;
        if (isLoadScripts) {
          isLoadScripts = false;
          loadScripts();
        }
      });

      function loadStyles() {

        // load required styles
        var head = document.getElementsByTagName("head")[0];

        if (options[currentPage].requiredStyles) {

          for (var i = 0; i < options[currentPage].requiredStyles.length; i++) {

            var cssNode = document.createElement('link');
            cssNode.type = 'text/css';
            cssNode.rel = 'stylesheet';
            cssNode.href = options[currentPage].requiredStyles[i];
            cssNode.media = 'screen';
            head.appendChild(cssNode);

            loadedStyles.push($(cssNode));
          }
        }

      }


      function loadScripts() {

        if (options[currentPage].requiredScripts && options[currentPage].requiredScripts.length > 0) {


          var numOfLoadedScripts = 0,
            numOfScripts = options[currentPage].requiredScripts.length;

          for (var i = 0; i < numOfScripts; i++) {

            $.getScript(options[currentPage].requiredScripts[i], function(script, textStatus) {

              numOfLoadedScripts++;
              if (numOfLoadedScripts == numOfScripts && options[currentPage].onLoad) {

                options[currentPage].onLoad();
              }
              loadedScripts.push($(this));
            });

          }
        } else {
          if (options[currentPage].onLoad) {
            options[currentPage].onLoad();
          }
        }
      }

    }
  };

})();
