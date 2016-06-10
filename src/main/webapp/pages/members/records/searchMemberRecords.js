Behaviour.register({
	
	'#typeSelect': function(select) {
		select.onchange = function() {
			select.form.submit();
		}
	},
	
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editMemberRecord?elementId=" + getValue("elementId") + "&typeId=" + getValue("typeId");
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			navigateToProfile(getValue("elementId"), elementNature);
		}
	},
    
    '#memberUsername': function(input) {
        var div = $('membersByUsername');
        prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'queryElementId', 'memberUsername', 'memberName');
    },
    
    '#memberName': function(input) {
        var div = $('membersByName');
        prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'queryElementId', 'memberUsername', 'memberName');
    },
    
    '#brokerUsername': function(input) {
        var div = $('brokersByUsername');
        prepareForMemberAutocomplete(input, div, {paramName:"username", brokers:true}, 'queryBrokerId', 'brokerUsername', 'brokerName');
    },
    
    '#brokerName': function(input) {
        var div = $('brokersByName');
        prepareForMemberAutocomplete(input, div, {paramName:"name", brokers:true}, 'queryBrokerId', 'brokerUsername', 'brokerName');
    },
	
	'img.memberRecordDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editMemberRecord?memberRecordId=" + img.getAttribute("memberRecordId") + "&global=" + getValue("global") ;
		}
	},

	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmation)) {
				self.location = pathPrefix + "/removeMemberRecord?memberRecordId=" + img.getAttribute("memberRecordId");
			}
		}
	},
	
	'img.exportCSV': function(img) {
        setPointer(img);
        img.onclick = function() {
            var form = document.forms[0];
            submitTo(form, pathPrefix + "/exportMemberRecordsToCsv");
        }
	}
	
});


Event.observe(self, "load", function() {
	setFocus("keywords")
});