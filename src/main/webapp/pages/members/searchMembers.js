function updateGroups() {
	if (typeof groupsSelect != 'undefined') {
		var groupFilters = getValue("query(groupFilters)");
		var params = arrayToParams(groupFilters, "groupFilterIds") + "&" + arrayToParams(["MEMBER", "BROKER"], "natures");
		var currentlySelected = ensureArray(getValue("query(groups)")); 
		findGroups(params, function(groups) {
			groupsSelect.values = groups.map(function(group) {
				return {text:group.name, value:group.id, selected: currentlySelected.include(group.id)};
			});
			groupsSelect.render();
		});
	}
}

Behaviour.register({
	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			printResults(form, pathPrefix + "/printMembers");
		}
	},
	
	'img.exportCSV': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			submitTo(form, pathPrefix + "/exportMembersToCsv");
		}
	},
	
	'#newMemberGroup': function(select) {
		select.onchange = function() {
			var groupId = this.value;
			if (!isEmpty(groupId)) {
				self.location = pathPrefix + "/createMember?groupId=" + groupId
			}
		}
	},

	'#brokerUsername': function(input) {
		var div = $('brokersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, brokers:true}, 'brokerId', 'brokerUsername', 'brokerName', 'comments');
	},

	'#brokerName': function(input) {
		var div = $('brokersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, brokers:true}, 'brokerId', 'brokerUsername', 'brokerName', 'comments');
	},
	
	'#backButton': function(button) {
	    button.onclick = function() {
	        history.back();
	    }
	}
});

Event.observe(self, "load", function() {
	getObject("query(keywords)").focus();
	var nmg = $("newMemberGroup");
	if (nmg) {
		nmg.selectedIndex = 0;
	}
});