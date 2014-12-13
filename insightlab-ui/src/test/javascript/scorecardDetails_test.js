describe('scorecardDetails test scorecard for model', function () {

    var scorecardDetails = null;

    beforeEach(function () {
        window.location.hash = "15562049452579152247/Scorecard_for_Model";
        scorecardDetails = new ScorecardDetails("widget-content");
    });

    afterEach(function () {
        window.location.hash = "";
        scorecardDetails = null;
        $("#widget-content").html('');
    });

    it("bradcrumb", function () {
        expect($('#widget-content div.acxiom-breadcrumb-container')).toBeTruthy();
    });

    it("widget check", function () {
        expect($('#Scorecard_for_Model-widget')).toBeTruthy();
    });

    it("table check", function () {
        expect($('#Scorecard_for_Model-widget tbody')).toBeTruthy();
    });
});

describe("scorecardDetails test Variables Contribution Chart", function () {
    
    var scorecardDetails = null;

    beforeEach(function () {
        window.location.hash = "15562049452579152247/Variables_Contribution_Chart";
        scorecardDetails = new ScorecardDetails("widget-content");
    });

    afterEach(function () {
        window.location.hash = "";
        scorecardDetails = null;
        $("#widget-content").html('');
    });
    
    it("bradcrumb", function () {
        expect($('#widget-content div.acxiom-breadcrumb-container')).toBeTruthy();
    });

    it("widget check", function () {
        expect($('#Variables_Contribution_Chart-widget')).toBeTruthy();
    });
});

describe("scorecardDetails test Gains Table", function () {
    var scorecardDetails = null;

    beforeEach(function () {
        window.location.hash = "15562049452579152247/Gains_Table";
        scorecardDetails = new ScorecardDetails("widget-content");
    });
    
    afterEach(function () {
        window.location.hash = "";
        scorecardDetails = null;
        $("#widget-content").html('');
    });

    it("bradcrumb", function () {
        expect($('#widget-content div.acxiom-breadcrumb-container')).toBeTruthy();
    });

    it("widget check", function () {
        expect($('#Gains_Table-widget')).toBeTruthy();
    });

    it("table check", function () {
        expect($('#Gains_Table-widget tbody')).toBeTruthy();
    });
});

describe("scorecardDetails test Lift Chart for Gains", function () {
    var scorecardDetails = null;

    beforeEach(function () {
        window.location.hash = "15562049452579152247/Lift_Chart_for_Gains";
        scorecardDetails = new ScorecardDetails("widget-content");
    });

    afterEach(function () {
        window.location.hash = "";
        scorecardDetails = null;
        $("#widget-content").html('');
    });

    it("bradcrumb", function () {
        expect($('#widget-content div.acxiom-breadcrumb-container')).toBeTruthy();
    });

    it("widget check", function () {
        expect($('#Lift_Chart_for_Gains-widget')).toBeTruthy();
    });
});

describe("scorecardDetails test Cumulative Gains Chart", function () {
    var scorecardDetails = null;

    beforeEach(function () {
        window.location.hash = "15562049452579152247/Cumulative_Gains_Chart";
        scorecardDetails = new ScorecardDetails("widget-content");
    });

    afterEach(function () {
        window.location.hash = "";
        scorecardDetails = null;
        $("#widget-content").html('');
    });
    
    it("bradcrumb", function () {
        expect($('#widget-content div.acxiom-breadcrumb-container')).toBeTruthy();
    });

    it("widget check", function () {
        expect($('#Cumulative_Gains_Chart-widget')).toBeTruthy();
    });
});

describe("scorecardDetails test Validations Gains Table", function () {
    var scorecardDetails = null;

    beforeEach(function () {
        window.location.hash = "15562049452579152247/Validation_Gains_Table";
        scorecardDetails = new ScorecardDetails("widget-content");
    });

    afterEach(function () {
        window.location.hash = "";
        scorecardDetails = null;
        $("#widget-content").html('');
    });

    it("bradcrumb", function () {
        expect($('#widget-content div.acxiom-breadcrumb-container')).toBeTruthy();
    });

    it("widget check", function () {
        expect($('#Validation_Gains_Table-widget')).toBeTruthy();
    });

    it("table check", function () {
        expect($('#Validation_Gains_Table-widget tbody')).toBeTruthy();
    });
});

describe("scorecardDetails test Lift Chart Validation Gain", function () {
    var scorecardDetails = null;

    beforeEach(function () {
        window.location.hash = "15562049452579152247/Lift_Chart_Validation_Gain";
        scorecardDetails = new ScorecardDetails("widget-content");
    });

    afterEach(function () {
        window.location.hash = "";
        scorecardDetails = null;
        $("#widget-content").html('');
    });

    it("bradcrumb", function () {
        expect($('#widget-content div.acxiom-breadcrumb-container')).toBeTruthy();
    });

    it("widget check", function () {
        expect($('#Lift_Chart_Validation_Gain-widget')).toBeTruthy();
    });
});

describe("scorecardDetails test Cumulative Gains Chart for Model", function () {
    var scorecardDetails = null;

    beforeEach(function () {
        window.location.hash = "15562049452579152247/Cumulative_Gains_Chart_for_Model";
        scorecardDetails = new ScorecardDetails("widget-content");
    });

    afterEach(function () {
        window.location.hash = "";
        scorecardDetails = null;
        $("#widget-content").html('');
    });

    it("bradcrumb", function () {
        expect($('#widget-content div.acxiom-breadcrumb-container')).toBeTruthy();
    });

    it("widget check", function () {
        expect($('#Cumulative_Gains_Chart_for_Model-widget')).toBeTruthy();
    });
});

describe("scorecardDetails test Decile chart", function () {
    var scorecardDetails = null;

    beforeEach(function () {
        window.location.hash = "15562049452579152247/Decile_Chart";
        scorecardDetails = new ScorecardDetails("widget-content");
    });

    afterEach(function () {
        window.location.hash = "";
        scorecardDetails = null;
        $("#widget-content").html('');
    });

    it("bradcrumb", function () {
        expect($('#widget-content div.acxiom-breadcrumb-container')).toBeTruthy();
    });

    it("widget check", function () {
        expect($('#Decile_Chart-widget')).toBeTruthy();
    });
});



