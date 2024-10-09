<template>
    <v-card outlined>
        <v-card-title>
            Move # {{item._links.self.href.split("/")[item._links.self.href.split("/").length - 1]}}
        </v-card-title>

        <v-card-text>
            <div>
                <String label="MoveId" v-model="item.moveId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="FamilyId" v-model="item.familyId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Role" v-model="item.role" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="StartRdnAddr" v-model="item.startRdnAddr" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="StartLocX" v-model="item.startLocX" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="StartLocY" v-model="item.startLocY" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="GoalLocX" v-model="item.goalLocX" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="GoalLocY" v-model="item.goalLocY" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="Distance" v-model="item.distance" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Number label="Duration" v-model="item.duration" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Path" v-model="item.path" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Status" v-model="item.status" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="CustomerId" v-model="item.customerId" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>

        <v-card-actions>
            <v-btn text color="deep-purple lighten-2" large @click="goList">List</v-btn>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="edit"
                    v-if="!editMode"
            >
                Edit
            </v-btn>
            <v-btn
                    color="primary"
                    text
                    @click="save"
                    v-else
            >
                Save
            </v-btn>
            <v-btn
                    color="primary"
                    text
                    @click="remove"
                    v-if="!editMode"
            >
                Delete
            </v-btn>
            <v-btn
                    color="primary"
                    text
                    @click="editMode = false"
                    v-if="editMode"
            >
                Cancel
            </v-btn>
        </v-card-actions>
    </v-card>
</template>


<script>
    const axios = require('axios').default;

    export default {
        name: 'MoveMoveDetail',
        components:{},
        props: {
        },
        data: () => ({
            item: null,
            editMode: false,
        }),
        async created() {
            var me = this;
            var params = this.$route.params;
            var temp = await axios.get(axios.fixUrl('/moves/' + params.id))
            if(temp.data) {
                me.item = temp.data
            }
        },
        methods: {
            goList() {
                var path = window.location.href.slice(window.location.href.indexOf("/#/"), window.location.href.lastIndexOf("/#"));
                path = path.replace("/#/", "/");
                this.$router.push(path);
            },
            edit() {
                this.editMode = true;
            },
            async remove(){
                try {
                    if (!this.offline) {
                        await axios.delete(axios.fixUrl(this.item._links.self.href))
                    }

                    this.editMode = false;

                    this.$emit('input', this.item);
                    this.$emit('delete', this.item);

                } catch(e) {
                    console.log(e)
                }
            },
        },
    };
</script>
