<template>
    <div>
        <v-list two-line v-if="list.length > 0">
            <v-list-item-group 
                    v-model="selected" 
                    color="primary"
                    @change="select"
            >
                <v-list-item v-for="(item, idx) in list" :key="idx">
                    <template v-slot:default="{ active }">
                        <v-list-item-avatar color="primary-darker-1">
                        </v-list-item-avatar>
                        
                        <v-list-item-content>
                            <v-list-item-title>
                            </v-list-item-title>
                            <v-list-item-subtitle>
                                MoveId :  {{item.moveId }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                FamilyId :  {{item.familyId }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                Role :  {{item.role }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                StartRdnAddr :  {{item.startRdnAddr }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                StartLocX :  {{item.startLocX }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                StartLocY :  {{item.startLocY }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                GoalLocX :  {{item.goalLocX }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                GoalLocY :  {{item.goalLocY }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                Distance :  {{item.distance }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                Duration :  {{item.duration }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                Path :  {{item.path }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                Status :  {{item.status }}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                CustomerId :  {{item.customerId }}
                            </v-list-item-subtitle>
                        </v-list-item-content>

                        <v-list-item-action>
                            <v-checkbox :input-value="active" color="primary-darker-1"></v-checkbox>
                        </v-list-item-action>
                    </template>
                </v-list-item>
            </v-list-item-group>
        </v-list>
    </div>
</template>


<script>
    const axios = require('axios').default;

    export default {
        name: 'MoveMovePicker',
        props: {
            value: [String, Object, Array, Number, Boolean],
        },
        data: () => ({
            list: [],
            selected: null,
        }),
        async created() {
            var me = this;
            var temp = await axios.get(axios.fixUrl('/moves'))
            if(temp.data) {
                me.list = temp.data._embedded.moves;
            }

            if(me.value && typeof me.value == "object" && Object.values(me.value)[0]) {
                var id = Object.values(me.value)[0];
                var tmpValue = await axios.get(axios.fixUrl('/moves/' + id))
                if(tmpValue.data) {
                    var val = tmpValue.data
                    me.list.forEach(function(item, idx) {
                        if(item.name == val.name) {
                            me.selected = idx
                        }
                    })
                }
            }
        },
        methods: {
            select(val) {
                var obj = {}
                if(val != undefined) {
                    var arr = this.list[val]._links.self.href.split('/');
                    obj['moveId'] = arr[4]; 
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    this.$emit('selected', obj);
                }
            },
        },
    };
</script>

