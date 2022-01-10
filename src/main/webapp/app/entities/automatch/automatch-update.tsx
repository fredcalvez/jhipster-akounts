import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBankCategory } from 'app/shared/model/bank-category.model';
import { getEntities as getBankCategories } from 'app/entities/bank-category/bank-category.reducer';
import { getEntity, updateEntity, createEntity, reset } from './automatch.reducer';
import { IAutomatch } from 'app/shared/model/automatch.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AutomatchUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankCategories = useAppSelector(state => state.bankCategory.entities);
  const automatchEntity = useAppSelector(state => state.automatch.entity);
  const loading = useAppSelector(state => state.automatch.loading);
  const updating = useAppSelector(state => state.automatch.updating);
  const updateSuccess = useAppSelector(state => state.automatch.updateSuccess);
  const handleClose = () => {
    props.history.push('/automatch');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBankCategories({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.updateTime = convertDateTimeToServer(values.updateTime);
    values.lastUsedTime = convertDateTimeToServer(values.lastUsedTime);

    const entity = {
      ...automatchEntity,
      ...values,
      category: bankCategories.find(it => it.id.toString() === values.category.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          updateTime: displayDefaultDateTime(),
          lastUsedTime: displayDefaultDateTime(),
        }
      : {
          ...automatchEntity,
          updateTime: convertDateTimeFromServer(automatchEntity.updateTime),
          lastUsedTime: convertDateTimeFromServer(automatchEntity.lastUsedTime),
          category: automatchEntity?.category?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.automatch.home.createOrEditLabel" data-cy="AutomatchCreateUpdateHeading">
            <Translate contentKey="akountsApp.automatch.home.createOrEditLabel">Create or edit a Automatch</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="automatch-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.automatch.matchstring')}
                id="automatch-matchstring"
                name="matchstring"
                data-cy="matchstring"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.automatch.updateTime')}
                id="automatch-updateTime"
                name="updateTime"
                data-cy="updateTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.automatch.lastUsedTime')}
                id="automatch-lastUsedTime"
                name="lastUsedTime"
                data-cy="lastUsedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('akountsApp.automatch.useCount')}
                id="automatch-useCount"
                name="useCount"
                data-cy="useCount"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.automatch.defaultTag')}
                id="automatch-defaultTag"
                name="defaultTag"
                data-cy="defaultTag"
                type="text"
              />
              <ValidatedField
                id="automatch-category"
                name="category"
                data-cy="category"
                label={translate('akountsApp.automatch.category')}
                type="select"
              >
                <option value="" key="0" />
                {bankCategories
                  ? bankCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/automatch" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AutomatchUpdate;
