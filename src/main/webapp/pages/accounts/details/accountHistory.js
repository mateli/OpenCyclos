function viewDetails() {
	var params = $H();
	params.set("memberId", getValue("memberId"));
	params.set("typeId", getValue("typeId"));
	params.set("transferId", this.getAttribute("transferId"));
	self.location = pathPrefix + "/viewTransaction?" + params.toQueryString();
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			var params = new QueryStringMap();
			if (booleanValue(params.get("fromQuickAccess"))) {
				self.location = pathPrefix + "/home";
			} else {
				self.location = context + backTo;
			}
		}
	},
	
	'#modeButton': function(button) {
		button.onclick = function() {
			var advanced = "true" == getValue("advanced");
			var params = $H();
			params.set("advanced", !advanced);
			params.set("memberId", getValue("query(owner)"));
			params.set("typeId", getValue("query(type)"));
			self.location = self.location.pathname + "?" + params.toQueryString();
		}
	},
	
	'img.exportCSV': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			submitTo(form, pathPrefix + "/exportAccountHistoryToCsv");
		}
	},
	
	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			printResults(form, pathPrefix + "/printAccountHistory");
		}
	},
	
	'img.not_conciliated': function(img) {
			img.title = titleNotConciliated;
	},

	'img.conciliated': function(img) {
			img.title = titleConciliated;
	},
	
	'img.view': function(img) {
		setPointer(img);
		img.onclick = viewDetails.bind(img);
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'queryMemberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'queryMemberId', 'memberUsername', 'memberName');
	},
	
	'#statusSelect': function(select) {
		select.onchange = function() {
			this.form.submit();
		}
	},
	
	'#filterSelect': function(select) {
		select.onchange = function() {
			this.form.submit();
		}
	}
});