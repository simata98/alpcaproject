<template>
    <div>
        <v-data-table
                :headers="headers"
                :items="values"
                :items-per-page="5"
                class="elevation-1"
        ></v-data-table>

        <v-col style="margin-bottom:40px;">
            <div class="text-center">
                <v-dialog
                        v-model="openDialog"
                        width="332.5"
                        fullscreen
                        hide-overlay
                        transition="dialog-bottom-transition"
                >
                    <template v-slot:activator="{ on, attrs }">
                        <v-fab-transition>
                            <v-btn
                                    color="primary"
                                    fab
                                    dark
                                    large
                                    style="position:absolute; bottom: 5%; right: 2%; z-index:99"
                                    @click="openDialog=true;"
                            >
                                <v-icon v-bind="attrs" v-on="on">mdi-plus</v-icon>
                            </v-btn>
                        </v-fab-transition>
                    </template>

                    <MoveMove :offline="offline" class="video-card" :isNew="true" :editMode="true" v-model="newValue" @add="append" v-if="tick"/>
                
                    <v-btn
                            style="postition:absolute; top:2%; right:2%"
                            @click="closeDialog()"
                            depressed 
                            icon 
                            absolute
                    >
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                </v-dialog>
            </div>
        </v-col>
    </div>
</template>

<script>
    const axios = require('axios').default;
    import MoveMove from './../MoveMove.vue';

    export default {
        name: 'MoveMoveManager',
        components: {
            MoveMove,
        },
        props: {
            offline: Boolean,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            values: [],
            headers: 
                [
                    { text: "moveId", value: "moveId" },
                    { text: "familyId", value: "familyId" },
                    { text: "role", value: "role" },
                    { text: "startRdnAddr", value: "startRdnAddr" },
                    { text: "startLocX", value: "startLocX" },
                    { text: "startLocY", value: "startLocY" },
                    { text: "goalLocX", value: "goalLocX" },
                    { text: "goalLocY", value: "goalLocY" },
                    { text: "distance", value: "distance" },
                    { text: "duration", value: "duration" },
                    { text: "path", value: "path" },
                    { text: "status", value: "status" },
                    { text: "customerId", value: "customerId" },
                ],
            move : [],
            newValue: {},
            tick : true,
            openDialog : false,
        }),
        async created() {
            if(this.offline){
                if(!this.values) this.values = [];
                return;
            }

            var temp = await axios.get(axios.fixUrl('/moves'))
            temp.data._embedded.moves.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])
            this.values = temp.data._embedded.moves;

            this.newValue = {
                'moveId': '',
                'familyId': '',
                'role': '',
                'startRdnAddr': '',
                'startLocX': 0,
                'startLocY': 0,
                'goalLocX': 0,
                'goalLocY': 0,
                'distance': 0,
                'duration': 0,
                'path': '',
                'status': '',
                'customerId': '',
            }
        },
        methods: {
            closeDialog(){
                this.openDialog = false
            },
            append(value){
                this.tick = false
                this.newValue = {}
                this.values.push(value)
                
                this.$emit('input', this.values);

                this.$nextTick(function(){
                    this.tick=true
                })
            },
        }
    }
</script>

