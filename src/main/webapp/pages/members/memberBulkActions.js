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
	
	'#actionSelect': function(select) {
		select.onchange = function() {
			var value = getValue(select)
			var showChangeGroup = value == 'changeGroup';
			var showChangeBroker = value == 'changeBroker';
			var showGenerateCard = value == 'generateCard';
			var showChangeChannels= value == 'changeChannels';
			
			var changeGroupDiv = $('changeGroupDiv')
			if (changeGroupDiv) {
				changeGroupDiv[showChangeGroup ? 'show' : 'hide']()
			}
			
			var changeBrokerDiv = $('changeBrokerDiv')
			if (changeBrokerDiv) {
				changeBrokerDiv[showChangeBroker ? 'show' : 'hide']()
			}
			
			var generateCardDiv = $('generateCardDiv')
			if (generateCardDiv) {
				generateCardDiv[showGenerateCard ? 'show' : 'hide']()
			}

			var changeChannelsDiv = $('changeChannelsDiv')
			if (changeChannelsDiv) {
				changeChannelsDiv[showChangeChannels ? 'show' : 'hide']()
			}
			
			if (showChangeGroup) {
				setFocus("newGroupSelect")
			} else if (showChangeBroker) {
				setFocus("newBrokerUsername")
			} else if (showChangeChannels) {
				enableChannels.render();
				disableChannels.render();
			}
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return false;
		}
	},
	
	'#previewButton': function(button) {
		button.onclick = function() {
			this.form.submit();
		}
	},
	
	'#brokerUsername': function(input) {
		var div = $('brokersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, brokers:true}, 'brokerId', 'brokerUsername', 'brokerName');
	},

	'#brokerName': function(input) {
		var div = $('brokersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, brokers:true}, 'brokerId', 'brokerUsername', 'brokerName');
	},
	
	'#newBrokerUsername': function(input) {
		var div = $('newBrokersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, brokers:true}, 'newBrokerId', 'newBrokerUsername', 'newBrokerName', 'changeBrokerComments');
	},

	'#newBrokerName': function(input) {
		var div = $('newBrokersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, brokers:true}, 'newBrokerId', 'newBrokerUsername', 'newBrokerName', 'changeBrokerComments');
	},

	'#changeBrokerComments': function(textarea) {
		new SizeLimit(textarea, 4000);
	},

	'#changeGroupComments': function(textarea) {
		new SizeLimit(textarea, 4000);
	},
	
	'#changeGroupButton': function(button) {
		button.onclick = function() {
			var form = this.form;
			var oldAction = form.action;
			try {
				form.action = pathPrefix + "/memberBulkChangeGroup";
				if (requestValidation(form) && confirm(confirmChangeGroup)) {
					form.submit();
				}
				return false;
			} finally {
				form.action = oldAction;
			}
		}
	},

	'#changeChannelsButton': function(button) {
		button.onclick = function() {
			var form = this.form;
			var oldAction = form.action;
			try {
				form.action = pathPrefix + "/memberBulkChangeChannels";
				if (requestValidation(form) && confirm(confirmChangeChannels)) {
					form.submit();
				}
				return false;
			} finally {
				form.action = oldAction;
			}
		}
	},
	
	'#changeBrokerButton': function(button) {
		button.onclick = function() {
			var form = this.form;
			var oldAction = form.action;
			try {
				form.action = pathPrefix + "/memberBulkChangeBroker";
				if (requestValidation(form) && confirm(confirmChangeBroker)) {
					form.submit();
				}
				return false;
			} finally {
				form.action = oldAction;
			}
		}
	},
	'#generateCardButton': function(button) {
		button.onclick = function() {
			var form = this.form;
			var oldAction = form.action;
			try {
				form.action = pathPrefix + "/memberBulkGenerateCard";
				if(confirm(confirmGenerateCard)){
					form.submit();
				}
				return false;
			} finally {
				form.action = oldAction;
			}
		}
	}
});

Event.observe(self, "load", function() {
	var newGroup = $("newGroupSelect");
	if (newGroup) {
		newGroup.selectedIndex = 0;
	}
	var action = $('actionSelect')
	action.selectedIndex = 0;
	action.onchange();
});