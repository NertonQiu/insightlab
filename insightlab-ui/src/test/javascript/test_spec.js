describe("Simple test", function () {
 var input;
	
//	beforeEach(function () {
//        var container = $(document).append('<div></div>',{id : 'widget-content'});
//        input = new InputTagComponent("widget-content");
//    });
//
//    afterEach(function () {
//        input = null;
//        $("#widget-content").html('');
//    });
    it("1+1 = 2?", function () {
        expect(1+1).toEqual(2);
    });
    
    it("1+1 = 2?", function () {
        expect(get2(1)).toEqual(2);
    });

//    it("Insight length (must be 1)", function () {
//        expect($('#widget-content input').length).toEqual(1);
//    });
});

