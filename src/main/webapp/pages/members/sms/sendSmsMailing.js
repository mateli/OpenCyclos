var insertAtCaret = function(myValue) {
	var textArea = $('text');
    if (document.selection) {
    	textArea.focus();
            sel = document.selection.createRange();
            sel.text = myValue;
            textArea.focus();
    }
    else if (textArea.selectionStart || textArea.selectionStart == '0') {
        var startPos = textArea.selectionStart;
        var endPos = textArea.selectionEnd;
        var scrollTop = textArea.scrollTop;
        textArea.value = textArea.value.substring(0, startPos)+myValue+textArea.value.substring(endPos,textArea.value.length);
        textArea.focus();
        textArea.selectionStart = startPos + myValue.length;
        textArea.selectionEnd = startPos + myValue.length;
        textArea.scrollTop = scrollTop;
    } else {
    	textArea.value += myValue;
    	textArea.focus();
    }
}

var hideShowTarget = function(isToGroup) {
	$$('tr.toHideMember').each(function(tr) {
   		tr[isToGroup ? 'hide' : 'show']();
   	});
	$$('tr.toHideGroups').each(function(tr) {
   		tr[!isToGroup ? 'hide' : 'show']();
   	});
	
	if (isToGroup) {
		setValue("memberId", null);
		setValue("memberUsername", null);
		setValue("memberName", null);

		if(typeof(groupsMdd) !== 'undefined'){
			if(groupsMdd != null){
				groupsMdd.render();
			}
		}
		
		try {
			$('trType').show();
			$('trFree').hide();
		} catch (e) {}
	} else {
		if(typeof(groupsMdd) !== 'undefined'){
			groupsMdd.values.each(function(value) {
			    value.selected = false;
			});	
			setValue("smsMailing(groups)", "");
		}		
		
		try {
			$('trType').hide();
			$('trFree').show();
		} catch (e) {}
	}
}

var updateVariables = function(){
	
	clearOptions('variables');
	
	var params;	
	var isToMember = $('toMember').checked;
	if(isToMember){
		var memberId = getValue("smsMailing(member)");
		if(memberId != ""){
			params = $H();
			params.set("memberId", memberId);
			params = params.toQueryString();
		}
	} else {
		var groups = getValue("smsMailing(groups)");
		if(groups == ""){
			if(typeof(groupsMdd) !== 'undefined'){
				var vals = [];
				for (var i=0; i<groupsMdd.values.length; i++) {
					vals.push(groupsMdd.values[i].value);
				}				
				params = arrayToParams(vals, "groupIds");
			}			
		} else{
			params = arrayToParams(groups, "groupIds");
		}
	}
	if((params !== undefined && params != "") || (isBroker && !isToMember)){
		new Ajax.Request(pathPrefix + "/searchSmsMailingVariablesAjax", {
			parameters: params,
			onSuccess: function(request, result) {
				var options = [];
				var listOfEntries = result.entries;
				for(var i = 0; i < listOfEntries.size(); i++){
					var e = listOfEntries[i];
					var internalName = e[0];
					var name = e[1];
					var option = new Option(name, internalName);
					options.push(option);
				}
				setOptions('variables', options);
			}
		});
	}
}

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			history.back();
		}
	},
	
	'#toMember': function(radio) {
		radio.onclick = function() {
			hideShowTarget(false);
			updateVariables();
		}
	},

	'#toGroup': function(radio) {
		radio.onclick = function() {
			hideShowTarget(true);
			updateVariables();
		}
	},

	'#text': function(text) {
		new SizeLimit(text, 160);
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, brokered:true}, 'memberId', 'memberUsername', 'memberName', 'text', updateVariables);
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, brokered:true}, 'memberId', 'memberUsername', 'memberName', 'text', updateVariables);
	},
	
	'#addButton': function(button) {
		button.onclick = function() {
			var sel = $('variables');
			insertAtCaret('#' + $('variables').value + '#');
		}		
	}	
	
});

Event.observe(self, "load", function() {
	updateVariables();
});

