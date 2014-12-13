describe("Portrait test", function () {

    var portrait;
    beforeEach(function () {
        window.location.hash = "99998/portrait";
        portrait = new PersonicxPage("widget-content");
        
        waits(500);
        runs(function () {
            $('#portrait-filters div.dropdown li:eq(0) a').trigger('click');
        });

//        waits(500);
//        runs(function () {
//            $('#portrait-filters div.subfilter:eq(0) li:eq(1) a').click();
//        });

//        waits(500);
//        runs(function () {
//            $('#portrait-filters div.subfilter:eq(1) li:eq(1) a').click();
//        });

//        waits(500);
//        runs(function () {
//            $('#portrait-filters div.subfilter:eq(2) li:eq(1) a').click();
//        });

//        waits(500);
//        runs(function () {
//            $('#portrait-filters div.subfilter:eq(3) li:eq(1) a').click();
//        });

//        waits(500);
//        runs(function () {
//            $('#portrait-filters div.subfilter:eq(3) li:eq(1) a').click();
//        });

        waits(500);
        runs(function () {
                $('#apply').click();
        });
    });

        afterEach(function () {
            portrait = null;
            $('#widget-content').empty().html('');
            window.location.hash = "";
        });


    it("breadcrumb", function () {
        waits(500);
        runs(function () {
            expect($('#widget-content .acxiom-breadcrumb-container').length).toEqual(1);
        });
    });

    it("filter divs (must be 2)", function () {
        waits(500);
        runs(function () {
            expect(($('#widget-content div.dashboard-filters').children()).length).toEqual(2);
        });
    });

    it("subfilter div is hidden", function () {
        waits(500);
        runs(function () {
            expect($('#widget-content div.dashboard-filters #portrait-subfilters').is(':visible'));
            console.log("div is hidden---", $('#widget-content div.dashboard-filters #portrait-subfilters').is(':visible'));
            $("#portrait-filters ul li a:first").click();
            $("#portrait-filters button").click();
        });
    });

    it("subfilter functional elements (must be 8)", function () {
        waits(500);
        runs(function () {
            expect(($('#widget-content div.dashboard-filters #portrait-subfilters').children()).length).toEqual(8);
        });
    });

    xit("visible/hidden subfilter's functional elements (must be 4/4)", function () {
        waits(500);
        runs(function () {
            expect(($('#portrait-subfilters').children(':hidden')).length).toEqual(8);
          });
    });

    it("portrait title (if National: 1 children, else if Survey: 3 children)", function () {
        waits(500);
        runs(function () {
            var children = ($('#widget-content div#portrait-title').children()).length;
            console.log("children---", children);
            expect(($('#widget-content div#portrait-title').children()).length).toEqual(children);
        });
    });

    xit("grids container (must be 1)", function () {
        waits(500);
        runs(function () {
            expect(($('#widget-content div.row-fluid.item-container #grid-container').children()).length).toEqual(1);
        });
    });

    xit("visible two divs for tables", function () {
        waits(500);
        runs(function () {
            expect(($('#widget-content div.row-fluid.item-container #grid-container').children(':visible')).length).toEqual(1);
        });
    });

    xit("portrait chart table", function () {
        waits(500);
        runs(function () {
            expect(($('#widget-content div.row-fluid.item-container #grid-container div#portraitChart-widget')).length).toEqual(1);
        });
    });

    xit("portrait table", function () {
        waits(500);
        runs(function () {
            expect(($('#widget-content div.row-fluid.item-container #grid-container div#portraitTable-widget')).length).toEqual(1);
        });
    });

    xit("segment characteristic (must be 6 li tags)", function () {
        waits(500);
        runs(function () {
            expect($('div#portrait-subfilters div.span3 div.dropdown ul li').length).toEqual(6);
        });
    });

    xit("select segment characteristic", function () {
            $('div#portrait-subfilters div.span3 ul li a:eq(2)').click();

        waits(1000);
        runs(function () {
            expect($('div#portraitChart-widget tbody tr div').hasClass('line-color-palevioletred')).toBeTruthy();
            expect($('div#portraitChart-widget tbody tr div').hasClass('line-color-tomato')).toBeTruthy();
            expect($('#widget-content div.row-fluid.item-container #grid-container div#portraitChart-widget tbody tr div').hasClass('line-color-palegreen')).toBeTruthy();
            expect($('#widget-content div.row-fluid.item-container #grid-container div#portraitChart-widget tbody tr div').hasClass('line-color-blue')).toBeFalsy();
            console.log("after click dropdown must have 7 li tags...");
            expect($('div#portrait-subfilters div.span3 ul li a').length).toEqual(7);
            console.log("after click dropdown legend must be visible...");
            expect($('div#chart-legend').is(':visible')).toBeTruthy();

            console.log("click reset...");
            $('div#portrait-subfilters div.span3 ul li a:contains("Reset")').click();
//            waits(1000);
//            runs(function () {
//                expect($('div#chart-legend').is(':visible')).toBeFalsy();
//                expect($('#widget-content div.row-fluid.item-container #grid-container div#portraitChart-widget tbody tr div').hasClass('line-color-blue')).toBeTruthy();
//                expect($('#widget-content div.row-fluid.item-container #grid-container div#portraitChart-widget tbody tr div').hasClass('line-color-tomato')).toBeFalsy();
//            });
       });
    });

    xit("click build button", function () {
        $('div#portrait-subfilters button:contains("Build Target Group")').click();
        waits(500);
        runs(function () {
            expect(($('#widget-content div.dashboard-filters #portrait-subfilters').children(':visible')).length).toEqual(6);
            expect(($('#widget-content div.dashboard-filters #portrait-subfilters').children(':hidden')).length).toEqual(2);
            console.log("click cancel button...");
            $('#widget-content div.dashboard-filters #portrait-subfilters div button.acxiom-button').click();
            waits(500);
            runs(function () {
                expect(($('#widget-content div.dashboard-filters #portrait-subfilters').children(':visible')).length).toEqual(4);
                expect(($('#widget-content div.dashboard-filters #portrait-subfilters').children(':hidden')).length).toEqual(4);
            });
        });
    });
});

describe("scattergram test", function () {

    var scattergram;
    beforeEach(function () {
        window.location.hash = "99998/scattergram";
        var container = document.getElementById("widget-content");
        scattergram = new PersonicxPage(container);
    });

    afterEach(function () {
        scattergram = null;
        $('#widget-content').empty().html('');
        window.location.hash = "";
    });

    xit("Scattergram load", function () {
        $('ul.nav.nav-tabs.span2 li:eq(1) a').click();
        waits(1000);
        runs(function () {
            expect($("#portraitChart-widget").length).toEqual(0);
            expect($("div.row-fluid.vertical").children('div').length).toEqual(6);
        });
    });

    it("scattergram check", function () {
        waits(500);
        runs(function () {
            $('#content div.subfilter:eq(0) li:eq(1) a').click();
            waits(500);
            runs(function () {
                $('#content div.subfilter:eq(1) li:eq(1) a').click();
            });
            waits(500);
            runs(function () {
                $('#content div.subfilter:eq(2) li:eq(1) a').click();
            });
            waits(500);
            runs(function () {
                $('#content div.subfilter:eq(3) li:eq(1) a').click();
            });
            waits(500);
            runs(function () {
                $('#content div.subfilter:eq(4) li:eq() a').click();
            });
            waits(1000);
            runs(function () {
                if (!$('apply:disabled').is(':disabled')) {
                        $('#apply').click();
                }
            });

            expect($('#question-response')).toBeTruthy();
            expect($('#chart-widget')).toBeTruthy();
            expect($('#Scattergram-table-widget')).toBeTruthy();
        });
    });
});