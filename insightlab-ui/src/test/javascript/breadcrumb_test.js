describe("breadcrumb test", function () {
    var breadcrumb;
    var breadcrumbConfig = [
        {
            'name': 'test',
            'link': "test"
        },
        {
            'name': 'test2'
        },
        {
            'name': 'test3',
            'link': 'link3'
        }
    ];

    beforeEach(function () {
        var container = document.getElementById("widget-content");
        breadcrumb = new Breadcrumb(container);
        breadcrumb.initWidget(breadcrumbConfig);
    });

    afterEach(function() {
        breadcrumb = null;
        $("#widget-content").empty().html("");
    });

    it("breadcrumb check", function () {
        expect($(".acxiom-breadcrumb-container")).toBeTruthy();
    });

    it("check breadcrumb elements", function () {
        expect($('#widget-content a').length).toEqual(2);
        expect($('#widget-content span').length).toEqual(3);
    });
});