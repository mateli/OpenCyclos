function getParams(includePage) {
    var map = new QueryStringMap();
    var params = $H();
    if (!isEmpty(map.get("admin"))) {
        params.set('admin', map.get("admin")); 
    }
    if (!isEmpty(map.get("member"))) {
        params.set('member', map.get("member")); 
    }
    if (!isEmpty(map.get("broker"))) {
        params.set('broker', map.get("broker")); 
    }
    if (includePage && !isEmpty(map.get("page"))) {
        params.set('page', map.get("page")); 
    }
    return params;
}

Behaviour.register({
	'#printManualLink': function(a) {
		setPointer(a);
		a.onclick = function() {
		    var params = getParams(false);
			win = window.open(context + "/do/print/manual?" + params.toQueryString());
		}
	},

	'#printSectionLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			var params = getParams(true);
			win = window.open(context + "/do/print/manual?" + params.toQueryString());
		}
	},
	
	'a.pageLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			var params = getParams(false);
			params.set('page', a.getAttribute('page'));
			self.location = pathPrefix + "/manual?" + params.toQueryString();
		}
	},
	
	'a.backLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			history.back()
		}
	}	
})